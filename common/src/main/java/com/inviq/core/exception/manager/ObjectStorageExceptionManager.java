package com.inviq.core.exception.manager;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 12/05/2026
 */
public class ObjectStorageExceptionManager extends AppException {
  public ObjectStorageExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum ObjectStorageError implements ExceptionType {
    FAILED_TO_UPLOAD_FILE("INV-OBJ-001", "Failed to upload file"),
    ;
    private final String code;
    private final String message;
  }
}
