package com.inviq.dao.affiliate.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 11/05/2026
 */
@Getter
@Setter
public class AffiliateFilter extends BaseFilterRequest {
  private String email;
  private String phoneNumber;
  private String affiliateTypeId;
}
