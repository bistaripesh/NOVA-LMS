package com.inviq.dto.affiliateType.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.AffiliateTypeExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 12/05/2026
 */
@Getter
@Setter
public class AffiliateTypeUpdateRequest extends BaseUpdateRequest {
  private String affiliateType;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(
          AffiliateTypeExceptionManager.AffiliateTypeError.AFFILIATE_TYPE_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.affiliateType))
      throw new AppException(AffiliateTypeExceptionManager.AffiliateTypeError.AFFILIATE_TYPE_EMPTY);
  }
}
