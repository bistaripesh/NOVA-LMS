package com.inviq.dao.qna.question.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 17/05/2026
 */
@Getter
@Setter
public class QuestionFilter extends BaseFilterRequest {
  private String year;
  private String levelSubjectMaterialId;
  private String levelSubjectId;
  private String levelId;
  private String subjectId;
  private String question;
  private Boolean isDraft;
}
