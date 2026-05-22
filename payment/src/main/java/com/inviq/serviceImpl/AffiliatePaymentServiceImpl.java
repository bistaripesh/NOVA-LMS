package com.inviq.serviceImpl;

import com.inviq.core.dto.response.Response;
import com.inviq.dao.PaymentDao;
import com.inviq.dao.entity.AffiliateReferralEntity;
import com.inviq.dao.entity.PaymentEntity;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.dto.response.AffiliatePaymentResponseDto;
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

  @Autowired
  public AffiliatePaymentServiceImpl(PaymentDao paymentDao) {
    this.paymentDao = paymentDao;
  }

  @Override
  public List<AffiliatePaymentResponseDto> getAffiliatePayments(PaymentFilter paymentFilter) {
    // Already has affiliateReferral inside each PaymentEntity
    PaymentFilter successFilter = new PaymentFilter();
    successFilter.setIsPaymentSuccess(true);
    List<PaymentEntity> successfulPayments = this.paymentDao.findAll(successFilter);

    // Group by referral and sum amounts
    Map<AffiliateReferralEntity, BigDecimal> totalsByReferral =
        successfulPayments.stream()
            .filter(p -> p.getAffiliateReferral() != null)
            .collect(
                Collectors.groupingBy(
                    PaymentEntity::getAffiliateReferral,
                    Collectors.reducing(
                        BigDecimal.ZERO, PaymentEntity::getAmount, BigDecimal::add)));

    // Map to DTO with commission
    List<AffiliatePaymentResponseDto> result =
        totalsByReferral.entrySet().stream()
            .map(
                entry -> {
                  AffiliateReferralEntity referral = entry.getKey();
                  BigDecimal total = entry.getValue();
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
                  return  dto;
                })
            .collect(Collectors.toList());

    return result;
  }
}
