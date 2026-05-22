package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.PaymentExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 22/05/2026
 */
@Getter
@Setter
public class AffiliatePaymentSettleRequest extends BaseUpdateRequest {

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.id))
      throw new AppException(PaymentExceptionManager.PaymentError.REFERRAL_ID_EMPTY);
  }
}
