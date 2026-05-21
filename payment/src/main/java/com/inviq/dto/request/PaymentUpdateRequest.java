package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.exception.PaymentExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 21/05/2026
 */
@Getter
@Setter
public class PaymentUpdateRequest extends BaseUpdateRequest {
  private Boolean isPaymentSuccess;
  private String levelSubjectId;
  private String userId;

  @Override
  public void validateRequest() {
    if (this.isPaymentSuccess == null)
      throw new AppException(PaymentExceptionManager.PaymentError.PAYMENT_SUCCESS_STATUS_EMPTY);
  }
}
