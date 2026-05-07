package com.inviq.dao.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 06/05/2026
 */
@Getter
@Setter
public class LevelSubjectMaterialFilter extends BaseFilterRequest {
  private String levelSubjectId;
  private String materialId;
}
