package com.inviq.core.exception.manager;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 12/05/2026
 */
public class PhoneNumberExceptionManager extends AppException {
  public PhoneNumberExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum PhoneNumberError implements ExceptionType {
    INVALID_PHONE_NUMBER("INV-PHN-INV-001", "Invalid phone number"),
    ;
    private final String code;
    private final String message;
  }
}
