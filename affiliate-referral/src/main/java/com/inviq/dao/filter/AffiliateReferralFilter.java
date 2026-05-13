package com.inviq.dao.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 13/05/2026
 */
@Getter
@Setter
public class AffiliateReferralFilter extends BaseFilterRequest {
  private String affiliateId;
  private Boolean isReferralCodeActive;
}
