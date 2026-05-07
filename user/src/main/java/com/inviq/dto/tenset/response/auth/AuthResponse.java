package com.inviq.dto.tenset.response.auth;

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
}
