package com.inviq.dto.tenset.response;

import com.inviq.core.dto.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 06/05/2026
 */
@Getter
@Setter
public class LevelSubjectMaterialSearchResponse extends BaseResponse {
  private String levelSubjectId;
  private String materialId;
  private String materialName;
  private String subjectName;
}
