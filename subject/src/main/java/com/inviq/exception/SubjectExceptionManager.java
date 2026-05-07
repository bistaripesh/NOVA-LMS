package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 05/05/2026
 */
public class SubjectExceptionManager extends AppException {
  public SubjectExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum SubjectError implements ExceptionType {
    SUBJECT_ID_EMPTY("INV-SUB-ID-001", "Subject id is empty"),
    SUBJECT_NAME_EMPTY("INV-SUB-NAM-001", "Subject name is empty"),
    ;

    private final String code;
    private final String message;
  }
}
