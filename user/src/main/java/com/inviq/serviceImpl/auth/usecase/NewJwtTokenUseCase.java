package com.inviq.serviceImpl.auth.usecase;

import com.inviq.core.constants.NovaLmsAppSecurityEnvConstants;
import com.inviq.core.exception.DynamicAppException;
import com.inviq.core.security.token.JWTTokenInfo;
import com.inviq.core.security.token.JWTTokenUtils;
import com.inviq.dto.tenset.request.auth.AuthResponse;
import com.inviq.dto.tenset.request.auth.JwtTokenRequest;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;

/**
 * @author ripesh created on 24/04/2026
 */
public class NewJwtTokenUseCase {

  public AuthResponse execute(JwtTokenRequest jwtTokenRequest) {
    jwtTokenRequest.validateRequest();
    Claims claims =
        JWTTokenUtils.getClaims(
            jwtTokenRequest.getRefreshToken(),
            true,
            NovaLmsAppSecurityEnvConstants.REFRESH_TOKEN_SECRET_KEY);
    JWTTokenInfo jwtTokenInfo =
        new JWTTokenInfo.JWTTokenBuilder().setClaim(claims).setSubject(claims.getSubject()).build();
    String token = JWTTokenUtils.getJWTToken(jwtTokenInfo);
    String refreshToken = this.getRefreshToken(jwtTokenInfo);
    return new AuthResponse(token, refreshToken, (String) claims.get("role"));
  }

  private String getRefreshToken(JWTTokenInfo jwtTokenInfo) {
    String refreshToken = "";
    try {
      refreshToken =
          JWTTokenUtils.getRefreshToken(
              jwtTokenInfo, true, NovaLmsAppSecurityEnvConstants.REFRESH_TOKEN_SECRET_KEY);
    } catch (Exception ex) {
      throw new DynamicAppException(
          HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Exception in refresh token generation");
    }
    return refreshToken;
  }
}
