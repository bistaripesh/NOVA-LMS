package com.inviq.dao.affiliateType.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 12/05/2026
 */
@Getter
@Setter
public class AffiliateTypeFilter extends BaseFilterRequest {
  private String affiliateType;
}
