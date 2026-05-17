package com.inviq.dto.qna.question.request;

import com.inviq.core.dto.request.crud.add.BaseAddRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.exception.qna.question.QuestionExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 17/05/2026
 */
@Getter
@Setter
public class QuestionAddRequest extends BaseAddRequest {
  private String question;
  private Integer year;
  private Boolean isDraft;
  private String levelSubjectMaterialId;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(this.question))
      throw new AppException(QuestionExceptionManager.QuestionError.QUESTION_EMPTY);
    if(StringUtil.isBlankOrNull(this.levelSubjectMaterialId))
      throw new AppException(QuestionExceptionManager.QuestionError.LEVEL_SUBJECT_MATERIAL_ID_EMPTY);
  }
}
