package com.inviq.dto.response;

import com.inviq.core.dto.response.BaseResponse;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 05/05/2026
 */
@Getter
@Setter
public class LevelSubjectsSearchResponse extends BaseResponse {
  private String levelId;
  private String levelName;
  private String subjectId;
  private String subjectName;
  private BigDecimal price;
}
