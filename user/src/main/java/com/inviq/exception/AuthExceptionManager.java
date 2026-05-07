package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 21/04/2025
 */
public class AuthExceptionManager extends AppException {

  public AuthExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @AllArgsConstructor
  @Getter
  public enum AuthError implements ExceptionType {
    PASSWORD_NOT_MATCHED("INV-AUT-PWD-001", "Password does not match"),
    WRONG_CREDENTIALS("INV-AUT-CRD-001", "Wrong credentials"),
    INVALID_REFRESH_TOKEN("INV-AUT-RFT-001", "Invalid refresh token"),
    ;
    private final String code;
    private final String message;
  }
}
