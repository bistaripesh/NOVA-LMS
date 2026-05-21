package com.inviq.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.PaymentGateWays;
import com.inviq.audit.dao.AuditDao;
import com.inviq.dao.AffiliateReferralDao;
import com.inviq.dao.LevelSubjectsDao;
import com.inviq.dao.PaymentDao;
import com.inviq.dao.entity.PaymentEntity;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.dto.request.LevelSubjectPaymentRequest;
import com.inviq.dto.request.LevelSubjectPaymentVerificationRequest;
import com.inviq.dto.response.MyPurchaseResponse;
import com.inviq.factory.PaymentFactory;
import com.inviq.service.LevelSubjectPaymentService;
import com.inviq.service.fonepay.FonePayService;
import com.inviq.serviceImpl.CacheService;
import com.inviq.usecase.LevelSubjectVerifyPaymentUseCase;
import com.inviq.usecase.LevelSubjectsInitiatePaymentUseCase;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 21/05/2026
 */
@Service
public class LevelSubjectPaymentServiceImpl implements LevelSubjectPaymentService {

  private final LevelSubjectsDao levelSubjectsDao;
  private final AffiliateReferralDao affiliateReferralDao;
  private final PaymentDao paymentDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;
  private final CacheService cacheService;
  private final PaymentFactory paymentFactory;

  @Autowired
  public LevelSubjectPaymentServiceImpl(
      LevelSubjectsDao levelSubjectsDao,
      AffiliateReferralDao affiliateReferralDao,
      PaymentDao paymentDao,
      AuditDao auditDao,
      ObjectMapper objectMapper,
      CacheService cacheService,
      PaymentFactory paymentFactory) {
    this.levelSubjectsDao = levelSubjectsDao;
    this.affiliateReferralDao = affiliateReferralDao;
    this.paymentDao = paymentDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
    this.cacheService = cacheService;
    this.paymentFactory = paymentFactory;
  }

  @Transactional
  @Override
  public Object initiatePayment(LevelSubjectPaymentRequest request) {
    return new LevelSubjectsInitiatePaymentUseCase(
            this.levelSubjectsDao,
            this.affiliateReferralDao,
            this.paymentDao,
            this.auditDao,
            (FonePayService) this.paymentFactory.getPaymentGateway(PaymentGateWays.FONE_PAY),
            this.cacheService,
            this.objectMapper)
        .execute(request);
  }

  @Override
  public Boolean verifyPayment(LevelSubjectPaymentVerificationRequest request) {
    return new LevelSubjectVerifyPaymentUseCase(
            this.paymentDao,
            this.auditDao,
            (FonePayService) this.paymentFactory.getPaymentGateway(PaymentGateWays.FONE_PAY),
            this.cacheService,
            this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public List<MyPurchaseResponse> getMyPurchases(String userId) {
    PaymentFilter filter = new PaymentFilter();
    filter.setUserId(userId);
    return this.paymentDao.findAll(filter).stream()
        .map(this::toMyPurchaseResponse)
        .toList();
  }

  private MyPurchaseResponse toMyPurchaseResponse(PaymentEntity payment) {
    String levelName =
        payment.getLevelSubjects() != null && payment.getLevelSubjects().getLevel() != null
            ? payment.getLevelSubjects().getLevel().getLevel()
            : null;
    String subjectName =
        payment.getLevelSubjects() != null && payment.getLevelSubjects().getSubject() != null
            ? payment.getLevelSubjects().getSubject().getSubject()
            : null;
    String levelSubjectId =
        payment.getLevelSubjects() != null ? payment.getLevelSubjects().getId() : null;

    return new MyPurchaseResponse(
        payment.getId(),
        levelSubjectId,
        levelName,
        subjectName,
        payment.getAmount(),
        payment.getIsPaymentSuccess(),
        payment.getCreatedOn());
  }
}
