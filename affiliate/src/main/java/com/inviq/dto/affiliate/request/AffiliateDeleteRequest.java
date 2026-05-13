package com.inviq.dto.affiliate.request;

import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.AffiliateExceptionManager;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateDeleteRequest extends BaseDeleteRequest {
  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(AffiliateExceptionManager.AffiliateError.AFFILIATE_ID_EMPTY);
  }
}
