package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.add.BaseAddRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.AffiliateReferralExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 13/05/2026
 */
@Getter
@Setter
public class AffiliateReferralAddRequest extends BaseAddRequest {
  private String affiliateId;
  private Integer commissionPercent;
  private String description;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.affiliateId))
      throw new AppException(
          AffiliateReferralExceptionManager.AffiliateReferralError.AFFILIATE_ID_EMPTY);
    if (commissionPercent == null)
      throw new AppException(
          AffiliateReferralExceptionManager.AffiliateReferralError.COMMISSION_PERCENTAGE_EMPTY);
    if (commissionPercent < 0 || commissionPercent > 100) {
      throw new AppException(
          AffiliateReferralExceptionManager.AffiliateReferralError.COMMISSION_PERCENTAGE_INVALID);
    }
  }
}
