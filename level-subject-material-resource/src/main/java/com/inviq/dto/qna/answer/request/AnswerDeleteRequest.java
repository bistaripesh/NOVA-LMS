package com.inviq.dto.qna.answer.request;

import com.inviq.core.dto.request.crud.delete.BaseDeleteRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.qna.answer.AnswerExceptionManager;

/**
 * @author ripesh created on 17/05/2026
 */
public class AnswerDeleteRequest extends BaseDeleteRequest {
  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(AnswerExceptionManager.AnswerError.ANSWER_ID_EMPTY);
  }
}
