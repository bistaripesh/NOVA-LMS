package com.inviq.service;

import com.inviq.dao.filter.PaymentFilter;
import com.inviq.dto.response.AffiliatePaymentResponse;
import java.util.List;

/**
 * @author ripesh created on 22/05/2026
 */
public interface AffiliatePaymentService {

  List<AffiliatePaymentResponse> getAffiliatePayments(PaymentFilter paymentFilter);

  void settleAffiliatePayment(String referralId);
}
