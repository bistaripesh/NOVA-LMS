package com.inviq.core.exception.manager;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 24/04/2026
 */
public class BasicRequestExceptionManager extends AppException {
  public BasicRequestExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum BasicRequestError implements ExceptionType {
    ID_MISSING_FOR_UPDATE("INV-REQ-UPD-001", "Id is missing for update"),
    ID_MISSING_FOR_DELETE("INV-REQ-DEL-001", "Id is missing for delete");

    private final String code;
    private final String message;
  }
}
