package com.inviq.dto.affiliateType.response;

import com.inviq.core.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 12/05/2026
 */
@Getter
@Setter
public class AffiliateTypeSearchResponse extends BaseResponse {
  private String affiliateType;
}
