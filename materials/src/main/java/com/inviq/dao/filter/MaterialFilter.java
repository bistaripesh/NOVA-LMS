package com.inviq.dao.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 05/05/2026
 */
@Getter
@Setter
public class MaterialFilter extends BaseFilterRequest {
  private String materialName;
}
