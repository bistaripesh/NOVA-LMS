package com.inviq.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ripesh created on 18/04/2025
 */
@RestControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(value = AppException.class)
  public ResponseEntity<ErrorData> handleAppException(AppException exception) {
    ExceptionType exceptionType = exception.getExceptionType();
    ErrorData errorData = new ErrorData();
    errorData.setCode(exceptionType.getCode());
    errorData.setMessage(exceptionType.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorData);
  }

  @ExceptionHandler(value = DynamicAppException.class)
  public ResponseEntity<ErrorData> handleDynamicAppException(DynamicAppException exception) {
    ErrorData errorData = new ErrorData();
    errorData.setCode(exception.getCode());
    errorData.setMessage(exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorData);
  }
}
