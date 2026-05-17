package com.inviq.dto.fonepay.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 14/05/2026
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FonePayLoginResponse {
  private String username;
  private String accessToken;
  private String refreshToken;
  private String tokenType;
  private int expiresIn;
}
