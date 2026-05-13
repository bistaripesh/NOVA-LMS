package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.AffiliateReferralExceptionManager;

/**
 * @author ripesh created on 13/05/2026
 */
public class AffiliateReferralDeleteRequest extends BaseDeleteRequest {
  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(
          AffiliateReferralExceptionManager.AffiliateReferralError.AFFILIATE_REFERRAL_ID_EMPTY);
  }
}
