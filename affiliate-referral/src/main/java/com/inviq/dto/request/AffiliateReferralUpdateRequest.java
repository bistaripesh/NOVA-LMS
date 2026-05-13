package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 13/05/2026
 */
@Getter
@Setter
public class AffiliateReferralUpdateRequest extends BaseUpdateRequest {
  private String description;

  @Override
  public void validateRequest() {}
}
