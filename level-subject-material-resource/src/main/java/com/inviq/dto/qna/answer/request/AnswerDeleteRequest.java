package com.inviq.dto.qna.answer.request;

import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
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
public class AnswerDeleteRequest extends BaseDeleteRequest {
  private String questionId;
  private Integer step;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(AnswerExceptionManager.AnswerError.ANSWER_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.questionId))
      throw new AppException(AnswerExceptionManager.AnswerError.QUESTION_ID_EMPTY);
    if (this.step == null)
      throw new AppException(AnswerExceptionManager.AnswerError.STEP_NUMBER_EMPTY);
  }
}
