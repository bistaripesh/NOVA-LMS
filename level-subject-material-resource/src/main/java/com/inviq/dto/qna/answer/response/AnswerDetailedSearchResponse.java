package com.inviq.dto.qna.answer.response;

import com.inviq.core.dto.response.Response;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 17/05/2026
 */
@Getter
@Setter
public class AnswerDetailedSearchResponse implements Response {
  private String questionId;
  private String question;
  private List<AnswerSearchResponse> answers;

  public AnswerDetailedSearchResponse() {
    this.answers = new ArrayList<>();
  }
}
