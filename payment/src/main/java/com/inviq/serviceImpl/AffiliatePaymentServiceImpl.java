package com.inviq.serviceImpl;

import com.inviq.core.exception.AppException;
import com.inviq.dao.AffiliateReferralDao;
import com.inviq.dao.PaymentDao;
import com.inviq.dao.entity.AffiliateReferralEntity;
import com.inviq.dao.entity.PaymentEntity;
import com.inviq.dao.filter.AffiliateReferralFilter;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.dto.response.AffiliatePaymentResponseDto;
import com.inviq.exception.PaymentExceptionManager;
import com.inviq.service.AffiliatePaymentService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 22/05/2026
 */
@Service
public class AffiliatePaymentServiceImpl implements AffiliatePaymentService {

  private final PaymentDao paymentDao;
  private final AffiliateReferralDao affiliateReferralDao;

  @Autowired
  public AffiliatePaymentServiceImpl(PaymentDao paymentDao, AffiliateReferralDao affiliateReferralDao) {
    this.paymentDao = paymentDao;
    this.affiliateReferralDao = affiliateReferralDao;
  }

  @Override
  public List<AffiliatePaymentResponseDto> getAffiliatePayments(PaymentFilter paymentFilter) {
    // Fetch all referrals (active and inactive) for this affiliate
    AffiliateReferralFilter referralFilter = new AffiliateReferralFilter();
    referralFilter.setAffiliateId(paymentFilter.getAffiliateId());
    List<AffiliateReferralEntity> allReferrals = this.affiliateReferralDao.findAll(referralFilter);

    // Fetch successful payments for this affiliate and group by referral id
    PaymentFilter successFilter = new PaymentFilter();
    successFilter.setIsPaymentSuccess(true);
    successFilter.setAffiliateId(paymentFilter.getAffiliateId());
    List<PaymentEntity> successfulPayments = this.paymentDao.findAll(successFilter);

    Map<String, BigDecimal> totalByReferralId =
        successfulPayments.stream()
            .filter(p -> p.getAffiliateReferral() != null)
            .collect(
                Collectors.groupingBy(
                    p -> p.getAffiliateReferral().getId(),
                    Collectors.reducing(
                        BigDecimal.ZERO, PaymentEntity::getAmount, BigDecimal::add)));

    // Build response for every referral; commission is 0 when no payments exist
    return allReferrals.stream()
        .map(referral -> {
          BigDecimal total = totalByReferralId.getOrDefault(referral.getId(), BigDecimal.ZERO);
          BigDecimal commission =
              total.multiply(
                  BigDecimal.valueOf(referral.getCommissionPercent())
                      .divide(BigDecimal.valueOf(100)));

          AffiliatePaymentResponseDto dto = new AffiliatePaymentResponseDto();
          dto.setId(referral.getId());
          dto.setReferralCode(referral.getReferralCode());
          dto.setCommissionPercent(referral.getCommissionPercent());
          dto.setFullName(referral.getAffiliate().getFullName());
          dto.setDescription(referral.getDescription());
          dto.setIsReferralCodeActive(referral.getIsReferralCodeActive());
          dto.setCommissionAmount(commission);
          dto.setIsTransactionSettled(referral.getIsTransactionSettled());
          return dto;
        })
        .collect(Collectors.toList());
  }

  @Override
  public void settleAffiliatePayment(String referralId) {
    AffiliateReferralEntity referral = this.affiliateReferralDao.findById(referralId)
        .orElseThrow(() -> new AppException(PaymentExceptionManager.PaymentError.REFERRAL_NOT_FOUND));
    referral.setIsTransactionSettled(true);
    this.affiliateReferralDao.update(referral);
  }
}
