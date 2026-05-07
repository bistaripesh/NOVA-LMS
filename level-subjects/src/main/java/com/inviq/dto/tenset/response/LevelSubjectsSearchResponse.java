package com.inviq.dto.tenset.response;

import com.inviq.core.dto.response.BaseResponse;
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
}
