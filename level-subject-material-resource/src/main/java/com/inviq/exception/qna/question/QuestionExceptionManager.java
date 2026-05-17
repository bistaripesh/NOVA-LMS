package com.inviq.exception.qna.question;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 17/05/2026
 */
public class QuestionExceptionManager extends AppException {
  public QuestionExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum QuestionError implements ExceptionType {
    QUESTION_ID_EMPTY("INV-QST-ID-001", "Question id is empty"),
    QUESTION_EMPTY("INV-QST-001", "Question is empty"),
    LEVEL_SUBJECT_MATERIAL_ID_EMPTY("INV-QST-002", "Level subject material id is empty"),
    ;

    private final String code;
    private final String message;
  }
}
