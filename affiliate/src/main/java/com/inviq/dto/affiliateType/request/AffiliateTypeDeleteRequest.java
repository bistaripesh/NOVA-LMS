package com.inviq.dto.affiliateType.request;

import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.AffiliateTypeExceptionManager;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateTypeDeleteRequest extends BaseDeleteRequest {
  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(
          AffiliateTypeExceptionManager.AffiliateTypeError.AFFILIATE_TYPE_ID_EMPTY);
  }
}
