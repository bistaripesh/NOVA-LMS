package com.inviq.dto.request;

import com.inviq.core.dto.request.crud.add.BaseAddRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.PaymentExceptionManager;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 21/05/2026
 */
@Getter
@Setter
public class PaymentAddRequest extends BaseAddRequest {
  private String levelSubjectId;
  private String userId;
  private BigDecimal amount;
  private String referenceLabel;
  private String referralId;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.levelSubjectId))
      throw new AppException(PaymentExceptionManager.PaymentError.LEVEL_SUBJECT_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.userId))
      throw new AppException(PaymentExceptionManager.PaymentError.USER_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.referenceLabel))
      throw new AppException(PaymentExceptionManager.PaymentError.REFERENCE_LABEL_EMPTY);
  }
}
