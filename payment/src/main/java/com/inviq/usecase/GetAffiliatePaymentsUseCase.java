package com.inviq.usecase;

import com.inviq.dao.AffiliateReferralDao;
import com.inviq.dao.PaymentDao;
import com.inviq.dao.entity.AffiliateReferralEntity;
import com.inviq.dao.entity.PaymentEntity;
import com.inviq.dao.filter.AffiliateReferralFilter;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.dto.response.AffiliatePaymentResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ripesh created on 23/05/2026
 */
public class GetAffiliatePaymentsUseCase {

  private final AffiliateReferralDao affiliateReferralDao;
  private final PaymentDao paymentDao;

  public GetAffiliatePaymentsUseCase(
      AffiliateReferralDao affiliateReferralDao, PaymentDao paymentDao) {
    this.affiliateReferralDao = affiliateReferralDao;
    this.paymentDao = paymentDao;
  }

  public List<AffiliatePaymentResponse> execute(PaymentFilter paymentFilter) {
    List<AffiliateReferralEntity> allReferrals =
        this.fetchReferrals(paymentFilter.getAffiliateId());
    List<PaymentEntity> successfulPayments =
        this.fetchSuccessfulPayments(paymentFilter.getAffiliateId());
    Map<String, BigDecimal> totalByReferralId = this.groupTotalByReferralId(successfulPayments);
    return this.buildResponse(allReferrals, totalByReferralId);
  }

  private List<AffiliateReferralEntity> fetchReferrals(String affiliateId) {
    AffiliateReferralFilter referralFilter = new AffiliateReferralFilter();
    referralFilter.setAffiliateId(affiliateId);
    return this.affiliateReferralDao.findAll(referralFilter);
  }

  private List<PaymentEntity> fetchSuccessfulPayments(String affiliateId) {
    PaymentFilter successFilter = new PaymentFilter();
    successFilter.setIsPaymentSuccess(true);
    successFilter.setAffiliateId(affiliateId);
    return this.paymentDao.findAll(successFilter);
  }

  private Map<String, BigDecimal> groupTotalByReferralId(List<PaymentEntity> successfulPayments) {
    return successfulPayments.stream()
        .filter(p -> p.getAffiliateReferral() != null)
        .collect(
            Collectors.groupingBy(
                p -> p.getAffiliateReferral().getId(),
                Collectors.reducing(BigDecimal.ZERO, PaymentEntity::getAmount, BigDecimal::add)));
  }

  private List<AffiliatePaymentResponse> buildResponse(
      List<AffiliateReferralEntity> allReferrals, Map<String, BigDecimal> totalByReferralId) {
    return allReferrals.stream()
        .map(referral -> this.toAffiliatePaymentResponse(referral, totalByReferralId))
        .collect(Collectors.toList());
  }

  private AffiliatePaymentResponse toAffiliatePaymentResponse(
      AffiliateReferralEntity referral, Map<String, BigDecimal> totalByReferralId) {
    BigDecimal total = totalByReferralId.getOrDefault(referral.getId(), BigDecimal.ZERO);
    BigDecimal commission =
        total.multiply(
            BigDecimal.valueOf(referral.getCommissionPercent())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
    AffiliatePaymentResponse response = new AffiliatePaymentResponse();
    response.setId(referral.getId());
    response.setReferralCode(referral.getReferralCode());
    response.setCommissionPercent(referral.getCommissionPercent());
    response.setFullName(referral.getAffiliate().getFullName());
    response.setDescription(referral.getDescription());
    response.setIsReferralCodeActive(referral.getIsReferralCodeActive());
    response.setCommissionAmount(commission);
    response.setIsTransactionSettled(referral.getIsTransactionSettled());
    return response;
  }
}
