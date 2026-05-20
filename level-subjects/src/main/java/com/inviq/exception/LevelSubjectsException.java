package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 05/05/2026
 */
public class LevelSubjectsException extends AppException {
  public LevelSubjectsException(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum LevelSubjectsError implements ExceptionType {
    LEVEL_SUBJECT_ID_EMPTY("INV-LVS-ID", "Level subject id is empty"),
    LEVEL_ID_EMPTY("INV-LVS-LID", "Level id is empty"),
    SUBJECT_ID_EMPTY("INV-LVS-SID", "Subject id is empty"),
    PRICE_EMPTY("INV-LVS-PRC", "Price is empty"),
    ;

    private final String code;
    private final String message;
  }
}
