package com.inviq.core.security.token;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 02/12/2023
 */
@Getter
@Setter
public class JWTTokenInfo {
  private Map<String, Object> claims;
  private String subject;

  public JWTTokenInfo(JWTTokenBuilder jwtTokenBuilder) {
    this.claims = jwtTokenBuilder.claims;
    this.subject = jwtTokenBuilder.subject;
  }

  public static class JWTTokenBuilder {
    private Map<String, Object> claims;
    private String subject;

    public JWTTokenBuilder setClaim(Map<String, Object> claim) {
      this.claims = new HashMap<>();
      claims.putAll(claim);
      return this;
    }

    public JWTTokenBuilder setSubject(String subject) {
      this.subject = subject;
      return this;
    }

    public JWTTokenInfo build() {
      return new JWTTokenInfo(this);
    }
  }
}
