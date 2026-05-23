package com.inviq.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ripesh created on 22/05/2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffiliatePaymentResponse {
  private String id;
  private String referralCode;
  private Integer commissionPercent;
  private String fullName;
  private String description;
  private Boolean isReferralCodeActive;
  private BigDecimal commissionAmount;
  private Boolean isTransactionSettled;
}
