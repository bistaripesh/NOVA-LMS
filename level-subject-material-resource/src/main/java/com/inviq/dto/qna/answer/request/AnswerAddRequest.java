package com.inviq.dto.qna.answer.request;

import com.inviq.core.dto.request.crud.add.BaseAddRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.qna.answer.AnswerExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 17/05/2026
 */
@Getter
@Setter
public class AnswerAddRequest extends BaseAddRequest {
  private Integer step;
  private String answer;
  private String questionId;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.questionId))
      throw new AppException(AnswerExceptionManager.AnswerError.QUESTION_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.answer))
      throw new AppException(AnswerExceptionManager.AnswerError.ANSWER_EMPTY);
    if (this.step == null)
      throw new AppException(AnswerExceptionManager.AnswerError.STEP_NUMBER_EMPTY);
  }
}
