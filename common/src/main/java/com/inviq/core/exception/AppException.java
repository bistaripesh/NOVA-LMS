package com.inviq.core.exception;

import lombok.Getter;

/**
 * @author ripesh created on 18/04/2025
 */
@Getter
public class AppException extends RuntimeException {

  private ExceptionType exceptionType;
  private String code;
  private String message;

  public AppException(ExceptionType exceptionType) {
    super(exceptionType.getMessage());
    this.exceptionType = exceptionType;
  }

  public AppException(String message) {
    super(message);
  }
}
