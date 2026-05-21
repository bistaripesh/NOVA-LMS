package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.constants.FonePayConstants;
import com.inviq.constants.FonePayEnvConstants;
import com.inviq.constants.FonePayPaymentMode;
import com.inviq.constants.FonePayQRType;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.entity.exception.DatabaseExceptionManager;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.core.util.SystemUtil;
import com.inviq.dao.AffiliateReferralDao;
import com.inviq.dao.LevelSubjectsDao;
import com.inviq.dao.PaymentDao;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dao.filter.AffiliateReferralFilter;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dto.fonepay.request.FonePayIntentQRRequest;
import com.inviq.dto.fonepay.request.FonePayLoginRequest;
import com.inviq.dto.fonepay.response.FonePayIntentQRResponse;
import com.inviq.dto.fonepay.response.FonePayLoginResponse;
import com.inviq.dto.request.LevelSubjectPaymentRequest;
import com.inviq.dto.request.PaymentAddRequest;
import com.inviq.exception.CacheExceptionManager;
import com.inviq.exception.FonePayExceptionManager;
import com.inviq.service.fonepay.FonePayService;
import com.inviq.serviceImpl.CacheService;
import com.inviq.usecase.PaymentAddUseCase;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ripesh created on 19/05/2026
 */
public class LevelSubjectsInitiatePaymentUseCase {

  private final LevelSubjectsDao levelSubjectsDao;
  private final AffiliateReferralDao affiliateReferralDao;
  private final PaymentDao paymentDao;
  private final AuditDao auditDao;
  private final FonePayService fonePayService;
  private final CacheService cacheService;
  private final ObjectMapper objectMapper;

  public LevelSubjectsInitiatePaymentUseCase(
      LevelSubjectsDao levelSubjectsDao,
      AffiliateReferralDao affiliateReferralDao,
      PaymentDao paymentDao,
      AuditDao auditDao,
      FonePayService fonePayService,
      CacheService cacheService,
      ObjectMapper objectMapper) {
    this.levelSubjectsDao = levelSubjectsDao;
    this.affiliateReferralDao = affiliateReferralDao;
    this.paymentDao = paymentDao;
    this.auditDao = auditDao;
    this.fonePayService = fonePayService;
    this.cacheService = cacheService;
    this.objectMapper = objectMapper;
  }

  public Object execute(LevelSubjectPaymentRequest request) {
    request.validateRequest();
    if (StringUtil.isNotBlankOrNull(request.getAffiliateReferralCode())) {
      AffiliateReferralFilter affiliateReferralFilter = new AffiliateReferralFilter();
      affiliateReferralFilter.setIsReferralCodeActive(true);
      affiliateReferralFilter.setReferralCode(request.getAffiliateReferralCode());

      this.affiliateReferralDao
          .findOne(affiliateReferralFilter)
          .orElseThrow(() -> new AppException("INV-REF-003", "Invalid Affiliate Referral Code"));
    }
    LevelSubjectsEntity levelSubject = this.fetchLevelSubject(request.getLevelSubjectId());
    this.ensureFonePayAuthenticated();
    FonePayIntentQRRequest qrRequest = this.buildQRRequest(levelSubject);
    FonePayIntentQRResponse qrResponse = (FonePayIntentQRResponse) this.requestIntentQR(qrRequest);
    this.savePayment(levelSubject, qrResponse, request.getAffiliateReferralCode());
    return qrResponse;
  }

  private void savePayment(
      LevelSubjectsEntity levelSubject,
      FonePayIntentQRResponse qrResponse,
      String affiliateReferralCode) {
    PaymentAddRequest paymentAddRequest = new PaymentAddRequest();
    paymentAddRequest.setLevelSubjectId(levelSubject.getId());
    paymentAddRequest.setUserId(getCurrentUserId());
    paymentAddRequest.setAmount(levelSubject.getPrice());
    paymentAddRequest.setReferenceLabel(qrResponse.getPrn());
    paymentAddRequest.setReferralCode(affiliateReferralCode);

    new PaymentAddUseCase(paymentDao, auditDao, objectMapper).execute(paymentAddRequest);
  }

  private LevelSubjectsEntity fetchLevelSubject(String levelSubjectId) {
    LevelSubjectsFilter filter = new LevelSubjectsFilter();
    filter.setId(levelSubjectId);
    return levelSubjectsDao
        .findOne(filter)
        .orElseThrow(
            () -> new AppException(DatabaseExceptionManager.DatabaseError.RECORD_NOT_FOUND));
  }

  private void ensureFonePayAuthenticated() {
    if (!cacheService.isCacheMiss(FonePayConstants.FONE_PAY_BEARER_TOKEN)) return;
    try {
      FonePayLoginResponse loginResp =
          fonePayService.oAuthLogin(
              new FonePayLoginRequest(
                  SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_USERNAME),
                  SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_PASSWORD)));
      if (cacheService.addStringData(
              FonePayConstants.FONE_PAY_BEARER_TOKEN,
              loginResp.getAccessToken(),
              loginResp.getExpiresIn())
          <= 0) throw new AppException(CacheExceptionManager.CacheError.FAILED_TO_ADD_TO_CACHE);
    } catch (Exception e) {
      throw new AppException(FonePayExceptionManager.FonePayError.FONEPAY_LOGIN_FAILED);
    }
  }

  private FonePayIntentQRRequest buildQRRequest(LevelSubjectsEntity levelSubject) {
    String billId =
        "BILL-" + levelSubject.getId() + getCurrentUserId() + System.currentTimeMillis();
    String refLabel = "REF" + System.currentTimeMillis();

    FonePayIntentQRRequest qrRequest = new FonePayIntentQRRequest();
    qrRequest.setBearerToken(cacheService.getStringData(FonePayConstants.FONE_PAY_BEARER_TOKEN));
    qrRequest.setAmount(levelSubject.getPrice());
    qrRequest.setBillId(billId);
    qrRequest.setReferenceLabel(refLabel);
    qrRequest.setPaymentMode(FonePayPaymentMode.QR.getCode());
    qrRequest.setFonePayQrType(FonePayQRType.INTENT_QR.getCode());
    // TODO remove this hard coded terminal id
    qrRequest.setTerminalId(SystemUtil.getEnv(FonePayEnvConstants.FONE_PAY_TERMINAL_ID));
    return qrRequest;
  }

  private Object requestIntentQR(FonePayIntentQRRequest qrRequest) {
    try {
      return fonePayService.getIntentQR(qrRequest);
    } catch (Exception e) {
      throw new AppException(FonePayExceptionManager.FonePayError.ERROR_REQUESTING_FONEPAY_QR);
    }
  }

  private String getCurrentUserId() {
    return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUserId();
  }
}
