package com.inviq.dto.tenset.request.auth;

import com.inviq.core.constants.NovaLmsAppSecurityEnvConstants;
import com.inviq.core.dto.request.crud.Request;
import com.inviq.core.exception.AppException;
import com.inviq.core.security.token.JWTTokenUtils;
import com.inviq.exception.AuthExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 24/04/2026
 */
@Getter
@Setter
public class JwtTokenRequest implements Request {
  private String refreshToken;

  @Override
  public void validateRequest() {
    if (!JWTTokenUtils.isValidJWTToken(
        this.refreshToken, true, NovaLmsAppSecurityEnvConstants.REFRESH_TOKEN_SECRET_KEY)) {
      throw new AppException(AuthExceptionManager.AuthError.INVALID_REFRESH_TOKEN);
    }
  }
}
