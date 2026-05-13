package com.inviq.dto.response;

import com.inviq.core.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 13/05/2026
 */
@Getter
@Setter
public class AffiliateReferralSearchResponse extends BaseResponse {
  private String referralCode;
  private Integer commissionPercent;
  private String fullName;
  private String description;
  private Boolean isReferralCodeActive;
}
