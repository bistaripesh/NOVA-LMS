package com.inviq.dao.qna.answer.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 17/05/2026
 */
@Getter
@Setter
public class AnswerFilter extends BaseFilterRequest {
  private String questionId;
  private Integer step;
  private String levelId;
  private String subjectId;
  private String levelSubjectId;
}
