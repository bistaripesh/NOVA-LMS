package com.inviq.dto.tenset.request.auth;

import com.inviq.core.dto.response.Response;
import lombok.*;

/**
 * @author ripesh created on 21/04/2025
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse implements Response {
  private String token;
  private String refreshToken;
  private String role;
  private String fullName;

  public AuthResponse(String token, String refreshToken, String role) {
    this.token = token;
    this.refreshToken = refreshToken;
    this.role = role;
  }
}
