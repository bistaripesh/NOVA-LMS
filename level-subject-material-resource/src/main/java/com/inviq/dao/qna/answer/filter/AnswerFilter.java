package com.inviq.dao.qna.answer.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import java.util.ArrayList;
import java.util.List;
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
  private List<Integer> steps;

  public AnswerFilter() {
    this.steps = new ArrayList<>();
  }
}
