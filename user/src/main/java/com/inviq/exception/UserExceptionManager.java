package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 23/04/2026
 */
public class UserExceptionManager extends AppException {
  public UserExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum UserError implements ExceptionType {
    USER_NOT_FOUND("INV-USR-UNF-001", "User not found"),
    PASSWORD_STANDARD_FAILED("INV-USR-PWD-001", "Password standard failed"),
    USER_FIRST_NAME_REQUIRED("INV-USR-FNR-001", "User first name is required"),
    USER_LAST_NAME_REQUIRED("INV-USR-LNR-001", "User last name is required"),
    ;
    private final String code;
    private final String message;
  }
}
