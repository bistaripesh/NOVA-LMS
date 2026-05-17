package com.inviq.exception.qna.answer;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 17/05/2026
 */
public class AnswerExceptionManager extends AppException {
  public AnswerExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum AnswerError implements ExceptionType {
    QUESTION_ID_EMPTY("INV-ANS-QST-001", "Question id is empty"),
    ANSWER_ID_EMPTY("INV-ANS-ID-001", "Answer id is empty"),
    ANSWER_EMPTY("INV-ANS-AEM-001", "Answer is empty"),
    STEP_NUMBER_EMPTY("INV-ANS-STE-001", "Step number is empty"),
    ONLY_LAST_STEP_CAN_BE_DELETED("INV-ANS-STE-002", "Only the last step can be deleted"), ;
    private final String code;
    private final String message;
  }
}
