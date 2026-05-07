package com.inviq.core.entity.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 18/04/2025
 */
public class DatabaseExceptionManager extends AppException {

  public DatabaseExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @AllArgsConstructor
  @Getter
  public enum DatabaseError implements ExceptionType {
    DUPLICATE_RECORD_FOUND("INV-DAB-DUP-001", "Duplicate record found"),
    FAILED_TO_INSERT_ENTITY("INV_DAB-INS-001", "Failed to add entity"),
    FAILED_TO_UPDATE_ENTITY("INV_DAB-UPD-001", "Failed to update entity"),
    FAILED_TO_DELETE_ENTITY("INV_DAB-DEL-001", "Failed to delete entity"),
    RECORD_NOT_FOUND("INV_DAB-REC-001", "Record not found"),
    ;
    private final String code;
    private final String message;
  }
}
