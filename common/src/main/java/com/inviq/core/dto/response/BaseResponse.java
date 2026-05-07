package com.inviq.core.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 05/04/2025
 */
@Getter
@Setter
public abstract class BaseResponse implements Response {
  private String id;
}
