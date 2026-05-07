package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 05/05/2026
 */
public class LevelExceptionManager extends AppException {
  public LevelExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum LevelError implements ExceptionType {
    LEVEL_ID_EMPTY("INV-LVL-ID-001", "Level id is empty"),
    LEVEL_NAME_EMPTY("INV-LVL-NAM-001", "Level name is empty"),
    ;

    private final String code;
    private final String message;
  }
}
