package com.inviq.dto.qna.question.response;

import com.inviq.core.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 17/05/2026
 */
@Getter
@Setter
public class QuestionSearchResponse extends BaseResponse {
  private String question;
  private Integer year;
  private Boolean isDraft;
  private String level;
  private String subject;
  private String levelSubjectMaterialId;
}
