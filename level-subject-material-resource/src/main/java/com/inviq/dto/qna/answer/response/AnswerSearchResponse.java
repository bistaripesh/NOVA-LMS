package com.inviq.dto.qna.answer.response;

import com.inviq.core.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 17/05/2026
 */
@Getter
@Setter
public class AnswerSearchResponse extends BaseResponse {
  private Integer step;
  private String answer;
}
