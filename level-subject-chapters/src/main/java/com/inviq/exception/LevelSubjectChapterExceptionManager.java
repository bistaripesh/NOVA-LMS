package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 07/05/2026
 */
public class LevelSubjectChapterExceptionManager extends AppException {
  public LevelSubjectChapterExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum LevelSubjectChapterError implements ExceptionType {
    LEVEL_SUBJECT_ID_EMPTY("INV-LSC-ID-001", "Level subject id is empty"),
    CHAPTER_ID_EMPTY("INV-LSC-CHP-001", "Chapter id is empty"),
    CHAPTER_NAME_EMPTY("INV-LSC-CHP-002", "Chapter name is empty"),
    CHAPTER_NUMBER_EMPTY("INV-LSC-CHP-003", "Chapter number is empty"),
    ;

    private final String code;
    private final String message;
  }
}
