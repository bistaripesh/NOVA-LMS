package com.inviq.security.constant;

import com.inviq.constants.WebConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 23/04/2026
 */
@Getter
@AllArgsConstructor
public enum PublicEndpoints {
  ERROR(WebConstant.APPLICATION_CONTEXT + "/error"),
  AUTH(WebConstant.APPLICATION_CONTEXT + "/auth"),
  AUTH_REFRESH_TOKEN(WebConstant.APPLICATION_CONTEXT + "/auth/refresh-token"),
  PING(WebConstant.APPLICATION_CONTEXT + "/ping"),
  PUBLIC_USER(WebConstant.APPLICATION_CONTEXT + "/public/user"),
  APPLICATION_CONTEXT(WebConstant.APPLICATION_CONTEXT + "/");

  private final String endpoint;
}
