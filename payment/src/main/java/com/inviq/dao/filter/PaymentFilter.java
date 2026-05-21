package com.inviq.dao.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 21/05/2026
 */
@Getter
@Setter
public class PaymentFilter extends BaseFilterRequest {
  private String levelSubjectId;
  private String referralCode;
  private String userId;
  private Boolean isPaymentSuccess;
}
