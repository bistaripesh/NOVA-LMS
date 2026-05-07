package com.inviq.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 15/04/2026
 */
@Setter
@Getter
@AllArgsConstructor
public class RestResponseBody {
  private boolean success;
  private Object data;
}
