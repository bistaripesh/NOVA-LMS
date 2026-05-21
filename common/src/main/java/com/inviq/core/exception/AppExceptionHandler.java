package com.inviq.core.exception;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ripesh created on 18/04/2025
 */
@RestControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ErrorData> handleAppException(AppException ex) {
    ErrorData errorData = new ErrorData();
    errorData.setCode(ex.getCode());
    errorData.setMessage(ex.getMessage());
    return ResponseEntity.badRequest().body(errorData);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorData> handleGenericException(Exception ex) {
    ErrorData errorData = new ErrorData();
    errorData.setCode("INTERNAL_SERVER_ERROR");
    errorData.setMessage("Something went wrong");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorData);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleDatabaseException(
      DataIntegrityViolationException ex) {
    String message = "Database error";
    Throwable rootCause = ex.getRootCause();
    if (rootCause != null && rootCause.getMessage().contains("foreign key")) {
      message = "Cannot perform action due to invalid reference or existing related records.";
    }
    Map<String, Object> response = new HashMap<>();
    response.put("code", "FOREIGN_KEY_CONSTRAINT");
    response.put("message", message);
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("code", "ENTITY_NOT_FOUND");
    response.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("code", "ACCESS_DENIED");
    response.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }
}
