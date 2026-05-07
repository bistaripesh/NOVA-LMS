package com.inviq.dao.filter;

import com.inviq.core.dto.filter.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 15/04/2026
 */
@Getter
@Setter
public class UserFilter extends BaseFilterRequest {
  private String email;
}
