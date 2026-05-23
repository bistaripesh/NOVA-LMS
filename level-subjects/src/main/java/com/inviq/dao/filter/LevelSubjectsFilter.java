package com.inviq.dao.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 05/05/2026
 */
@Getter
@Setter
public class LevelSubjectsFilter extends BaseFilterRequest {
  private String levelId;
  private String subjectId;
  private String subjectName;
  private BigDecimal price;
}
