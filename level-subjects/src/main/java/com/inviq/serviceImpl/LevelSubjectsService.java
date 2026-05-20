package com.inviq.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.PaymentGateWays;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.AffiliateReferralDao;
import com.inviq.dao.LevelSubjectsDao;
import com.inviq.dao.converter.LevelSubjectsEntityConverter;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dto.request.LevelSubjectPaymentRequest;
import com.inviq.dto.request.LevelSubjectsAddRequest;
import com.inviq.dto.request.LevelSubjectsDeleteRequest;
import com.inviq.dto.request.LevelSubjectsUpdateRequest;
import com.inviq.factory.PaymentFactory;
import com.inviq.service.LevelSubjectPaymentService;
import com.inviq.service.LevelSubjectsCrudService;
import com.inviq.service.fonepay.FonePayService;
import com.inviq.serviceImpl.usecase.LevelSubjectUpdateUseCase;
import com.inviq.serviceImpl.usecase.LevelSubjectsAddUseCase;
import com.inviq.serviceImpl.usecase.LevelSubjectsDeleteUseCase;
import com.inviq.serviceImpl.usecase.LevelSubjectsInitiatePaymentUseCase;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 05/05/2026
 */
@Service
public class LevelSubjectsService implements LevelSubjectsCrudService, LevelSubjectPaymentService {

  private final LevelSubjectsDao levelSubjectsDao;
  private final AffiliateReferralDao affiliateReferralDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;
  private final CacheService cacheService;
  private final PaymentFactory paymentFactory;

  @Autowired
  public LevelSubjectsService(
      LevelSubjectsDao levelSubjectsDao,
      AffiliateReferralDao affiliateReferralDao,
      AuditDao auditDao,
      ObjectMapper objectMapper,
      CacheService cacheService,
      PaymentFactory paymentFactory) {
    this.levelSubjectsDao = levelSubjectsDao;
    this.affiliateReferralDao = affiliateReferralDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
    this.cacheService = cacheService;
    this.paymentFactory = paymentFactory;
  }

  @Transactional
  @Override
  public Response create(LevelSubjectsAddRequest request) {
    return new LevelSubjectsAddUseCase(this.levelSubjectsDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response update(LevelSubjectsUpdateRequest request) {
    return new LevelSubjectUpdateUseCase(this.levelSubjectsDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response delete(LevelSubjectsDeleteRequest request) {
    return new LevelSubjectsDeleteUseCase(this.levelSubjectsDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(LevelSubjectsFilter request) {
    return this.levelSubjectsDao
        .findOne(request)
        .map(entity -> new LevelSubjectsEntityConverter().getResponse(entity));
  }

  @Override
  public List<Response> findAll(LevelSubjectsFilter request) {
    return new LevelSubjectsEntityConverter().getResponses(this.levelSubjectsDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(LevelSubjectsFilter levelSubjectsFilter) {
    return this.levelSubjectsDao.findWithPagination(levelSubjectsFilter);
  }

  @Override
  public Object initiatePayment(LevelSubjectPaymentRequest request) {
    return new LevelSubjectsInitiatePaymentUseCase(
            this.levelSubjectsDao,
            this.affiliateReferralDao,
            (FonePayService) this.paymentFactory.getPaymentGateway(PaymentGateWays.FONE_PAY),
            this.cacheService)
        .execute(request);
  }
  //    LevelSubjectsFilter filter = new LevelSubjectsFilter();
  //    filter.setId(request.getLevelSubjectId());
  //    LevelSubjectsEntity levelSubjectEntity =
  //        this.levelSubjectsDao
  //            .findOne(filter)
  //            .orElseThrow(
  //                () -> new
  // AppException(DatabaseExceptionManager.DatabaseError.RECORD_NOT_FOUND));
  //
  //    if (this.cacheService.isCacheMiss(FonePayConstants.FONE_PAY_BEARER_TOKEN)) {
  //      FonePayLoginResponse loginResp;
  //      try {
  //        loginResp = fonePayService.oAuthLogin(new FonePayLoginRequest("esewa", "password"));
  //        this.cacheService.addStringData(
  //            FonePayConstants.FONE_PAY_BEARER_TOKEN,
  //            loginResp.getAccessToken(),
  //            loginResp.getExpiresIn());
  //      } catch (Exception e) {
  //        throw new AppException(FonePayExceptionManager.FonePayError.FONEPAY_LOGIN_FAILED);
  //      }
  //    }
  //    FonePayIntentQRRequest fonePayIntentQRRequest = new FonePayIntentQRRequest();
  //    fonePayIntentQRRequest.setBearerToken(
  //        this.cacheService.getStringData(FonePayConstants.FONE_PAY_BEARER_TOKEN));
  //    fonePayIntentQRRequest.setAmount(levelSubjectEntity.getPrice());
  //    String billId =
  //        "BILL-"
  //            + levelSubjectEntity.getId()
  //            + ((UserPrincipal)
  //                    SecurityContextHolder.getContext().getAuthentication().getPrincipal())
  //                .getUserId()
  //            + System.currentTimeMillis();
  //    fonePayIntentQRRequest.setBillId(billId);
  //    fonePayIntentQRRequest.setPaymentMode(FonePayPaymentMode.QR.getCode());
  //    fonePayIntentQRRequest.setReferenceLabel("REF" + System.currentTimeMillis());
  //    fonePayIntentQRRequest.setFonePayQrType(FonePayQRType.INTENT_QR.getCode());
  //    fonePayIntentQRRequest.setTerminalId("4271420000001762");
  //    try {
  //
  //      return this.fonePayService.getIntentQR(fonePayIntentQRRequest);
  //    } catch (Exception e) {
  //      throw new AppException(FonePayExceptionManager.FonePayError.ERROR_REQUESTING_FONEPAY_QR);
  //    }
  //  }
}
