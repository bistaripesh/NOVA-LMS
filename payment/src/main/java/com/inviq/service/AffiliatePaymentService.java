package com.inviq.service;

import com.inviq.core.dto.response.Response;
import com.inviq.dao.filter.AffiliateReferralFilter;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.dto.response.AffiliatePaymentResponseDto;

import java.util.List;

/**
 * @author ripesh created on 22/05/2026
 */
public interface AffiliatePaymentService {

  List<AffiliatePaymentResponseDto> getAffiliatePayments(PaymentFilter paymentFilter);
}
