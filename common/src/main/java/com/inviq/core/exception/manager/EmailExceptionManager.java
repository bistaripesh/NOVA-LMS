package com.inviq.core.exception.manager;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 24/04/2026
 */
public class EmailExceptionManager extends AppException {
  public EmailExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum EmailValidationError implements ExceptionType {
    INVALID_EMAIL("INV-EML-INV-001", "Invalid email"),
    ;

    private final String code;
    private final String message;
  }
}
