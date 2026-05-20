package com.inviq.core.exception;

import lombok.Getter;

/**
 * @author ripesh created on 18/04/2025
 */
@Getter
public class AppException extends RuntimeException {

  private final String code;

  public AppException(ExceptionType exceptionType) {
    super(exceptionType.getMessage());
    this.code = exceptionType.getCode();
  }

  public AppException(String code, String message) {
    super(message);
    this.code = code;
  }
}
