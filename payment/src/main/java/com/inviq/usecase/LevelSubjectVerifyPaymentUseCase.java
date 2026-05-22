package com.inviq.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.constants.FonePayConstants;
import com.inviq.constants.FonePayEnvConstants;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.SystemUtil;
import com.inviq.dao.PaymentDao;
import com.inviq.dao.entity.PaymentEntity;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.dto.fonepay.request.FonePayLoginRequest;
import com.inviq.dto.fonepay.request.FonePayPaymentStatusRequest;
import com.inviq.dto.fonepay.response.FonePayLoginResponse;
import com.inviq.dto.fonepay.response.FonePayPaymentStatusResponse;
import com.inviq.dto.request.LevelSubjectPaymentVerificationRequest;
import com.inviq.dto.request.PaymentUpdateRequest;
import com.inviq.exception.CacheExceptionManager;
import com.inviq.exception.FonePayExceptionManager;
import com.inviq.service.fonepay.FonePayService;
import com.inviq.serviceImpl.CacheService;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ripesh created on 21/05/2026
 */
public class LevelSubjectVerifyPaymentUseCase {

  private final PaymentDao paymentDao;
  private final FonePayService fonePayService;
  private final CacheService cacheService;
  private final PaymentUpdateUseCase paymentUpdateUseCase;

  public LevelSubjectVerifyPaymentUseCase(
      PaymentDao paymentDao,
      AuditDao auditDao,
      FonePayService fonePayService,
      CacheService cacheService,
      ObjectMapper objectMapper) {
    this.paymentDao = paymentDao;
    this.fonePayService = fonePayService;
    this.cacheService = cacheService;
    this.paymentUpdateUseCase = new PaymentUpdateUseCase(this.paymentDao, auditDao, objectMapper);
  }

  public Boolean execute(LevelSubjectPaymentVerificationRequest request) {
    request.validateRequest();

    List<PaymentEntity> payments = this.fetchPayments(request.getLevelSubjectId());
    if (payments.isEmpty()) {
      return false;
    }

    if (hasSuccessfulPayment(payments)) {
      return true;
    }

    ensureFonePayAuthenticated();

    return verifyAndUpdatePendingPayments(payments);
  }

  private List<PaymentEntity> fetchPayments(String levelSubjectId) {
    PaymentFilter filter = new PaymentFilter();
    filter.setLevelSubjectId(levelSubjectId);
    filter.setUserId(getCurrentUserId());

    List<PaymentEntity> payments = paymentDao.findAll(filter);
    return payments != null ? payments : Collections.emptyList();
  }

  private boolean hasSuccessfulPayment(List<PaymentEntity> payments) {
    return payments.stream().anyMatch(this::isPaymentSuccessful);
  }

  private boolean verifyAndUpdatePendingPayments(List<PaymentEntity> payments) {
    return payments.stream()
        .filter(payment -> !isPaymentSuccessful(payment))
        .anyMatch(this::verifyAndMarkIfSuccessful);
  }

  private boolean verifyAndMarkIfSuccessful(PaymentEntity payment) {
    FonePayPaymentStatusRequest statusRequest = buildStatusRequest(payment);
    try {
      FonePayPaymentStatusResponse response = fonePayService.getPaymentStatus(statusRequest);
      if (response != null
          && FonePayConstants.FONE_PAY_PAYMENT_STATUS.equalsIgnoreCase(
              response.getPaymentStatus())) {
        markPaymentSuccessful(payment);
        return true;
      }
      return false;
    } catch (Exception e) {

      return false;
    }
  }

  private FonePayPaymentStatusRequest buildStatusRequest(PaymentEntity payment) {
    FonePayPaymentStatusRequest statusRequest = new FonePayPaymentStatusRequest();
    statusRequest.setBearerToken(
        cacheService.getStringData(FonePayConstants.FONE_PAY_BEARER_TOKEN));
    statusRequest.setReferenceLabel(payment.getReferenceLabel());
    statusRequest.setTerminalId(SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_TERMINAL_ID));
    return statusRequest;
  }

  private void markPaymentSuccessful(PaymentEntity payment) {
    PaymentUpdateRequest updateRequest = new PaymentUpdateRequest();
    updateRequest.setId(payment.getId());
    updateRequest.setIsPaymentSuccess(true);
    updateRequest.setLevelSubjectId(payment.getLevelSubjects().getId());
    updateRequest.setUserId(payment.getUser().getId());
    paymentUpdateUseCase.execute(updateRequest);
  }

  private boolean isPaymentSuccessful(PaymentEntity payment) {
    return Boolean.TRUE.equals(payment.getIsPaymentSuccess());
  }

  private void ensureFonePayAuthenticated() {
    boolean tokenExists = !cacheService.isCacheMiss(FonePayConstants.FONE_PAY_BEARER_TOKEN);
    if (tokenExists) return;
    try {
      FonePayLoginResponse loginResp =
          fonePayService.oAuthLogin(
              new FonePayLoginRequest(
                  SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_USERNAME),
                  SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_PASSWORD)));
      boolean stored =
          cacheService.addStringData(
                  FonePayConstants.FONE_PAY_BEARER_TOKEN,
                  loginResp.getAccessToken(),
                  loginResp.getExpiresIn())
              > 0;
      if (!stored) {
        throw new AppException(CacheExceptionManager.CacheError.FAILED_TO_ADD_TO_CACHE);
      }
    } catch (AppException e) {
      throw e;
    } catch (Exception e) {
      throw new AppException(FonePayExceptionManager.FonePayError.FONEPAY_LOGIN_FAILED);
    }
  }

  private String getCurrentUserId() {
    return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUserId();
  }
}
