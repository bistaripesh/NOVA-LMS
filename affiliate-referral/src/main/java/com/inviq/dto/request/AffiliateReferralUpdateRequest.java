package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.exception.AffiliateReferralExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 13/05/2026
 */
@Getter
@Setter
public class AffiliateReferralUpdateRequest extends BaseUpdateRequest {
  private String description;
  private Boolean isTransactionSettled;

  @Override
  public void validateRequest() {
    if (this.isTransactionSettled == null)
      throw new AppException(
          AffiliateReferralExceptionManager.AffiliateReferralError
              .TRANSACTION_SETTLEMENT_STATUS_EMPTY);
  }
}
