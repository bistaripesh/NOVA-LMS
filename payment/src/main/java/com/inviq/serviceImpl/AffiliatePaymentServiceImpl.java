package com.inviq.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.dao.AffiliateReferralDao;
import com.inviq.dao.PaymentDao;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.dto.request.AffiliateReferralUpdateRequest;
import com.inviq.dto.response.AffiliatePaymentResponse;
import com.inviq.service.AffiliatePaymentService;
import com.inviq.serviceImpl.usecase.AffiliateReferralUpdateUseCase;
import com.inviq.usecase.GetAffiliatePaymentsUseCase;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 22/05/2026
 */
@Service
public class AffiliatePaymentServiceImpl implements AffiliatePaymentService {

  private final PaymentDao paymentDao;
  private final AffiliateReferralDao affiliateReferralDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public AffiliatePaymentServiceImpl(
      PaymentDao paymentDao,
      AffiliateReferralDao affiliateReferralDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    this.paymentDao = paymentDao;
    this.affiliateReferralDao = affiliateReferralDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Override
  public List<AffiliatePaymentResponse> getAffiliatePayments(PaymentFilter paymentFilter) {
    return new GetAffiliatePaymentsUseCase(this.affiliateReferralDao, this.paymentDao)
        .execute(paymentFilter);
  }

  @Transactional
  @Override
  public void settleAffiliatePayment(String referralId) {
    AffiliateReferralUpdateRequest request = new AffiliateReferralUpdateRequest();
    request.setId(referralId);
    request.setIsTransactionSettled(true);
    new AffiliateReferralUpdateUseCase(this.affiliateReferralDao, this.auditDao, this.objectMapper)
        .execute(request);
  }
}
