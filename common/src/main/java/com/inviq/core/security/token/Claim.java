package com.inviq.core.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 02/12/2023
 */
@Getter
@Setter
public class Claim {

  private Claims claims;
  private String subject;

  public Claim(ClaimBuilder claimBuilder) {
    claims = Jwts.claims().subject(claimBuilder.subject).build();
    claims.putAll(claimBuilder.map);
    this.subject = claimBuilder.subject;
  }

  public static class ClaimBuilder {
    private final Map<String, Object> map;
    private String subject;

    public ClaimBuilder() {
      this.map = new HashMap<>();
    }

    public ClaimBuilder addClaim(String key, Object value) {
      map.put(key, value);
      return this;
    }

    public ClaimBuilder addSubject(String subject) {
      this.subject = subject;
      return this;
    }

    public Claim build() {
      return new Claim(this);
    }
  }
}
