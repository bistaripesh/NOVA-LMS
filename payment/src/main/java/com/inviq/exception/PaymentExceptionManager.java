package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 21/05/2026
 */
public class PaymentExceptionManager extends AppException {
  public PaymentExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum PaymentError implements ExceptionType {
    LEVEL_SUBJECT_ID_EMPTY("INV-LVS-ID", "Level subject id is empty"),
    USER_ID_EMPTY("INV-LVS-ID", "User id is empty"),
    PAYMENT_ID_EMPTY("INV-LVS-ID", "Payment id is empty"),
    REFERENCE_LABEL_EMPTY("INV-LVS-ID", "Reference label is empty"),
    PAYMENT_SUCCESS_STATUS_EMPTY("INV-LVS-ID", "Payment success status is empty"),
    ;

    private final String code;
    private final String message;
  }
}
