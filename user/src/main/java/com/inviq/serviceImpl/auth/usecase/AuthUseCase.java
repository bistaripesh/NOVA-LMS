package com.inviq.serviceImpl.auth.usecase;

import com.inviq.core.constants.NovaLmsAppSecurityEnvConstants;
import com.inviq.core.exception.AppException;
import com.inviq.core.security.token.JWTTokenInfo;
import com.inviq.core.security.token.JWTTokenUtils;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.UserDao;
import com.inviq.dao.entity.UserEntity;
import com.inviq.dao.filter.UserFilter;
import com.inviq.dto.tenset.request.auth.AuthRequest;
import com.inviq.dto.tenset.request.auth.AuthResponse;
import com.inviq.exception.AuthExceptionManager;
import com.inviq.exception.UserExceptionManager;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author ripesh created on 23/04/2026
 */
public class AuthUseCase {

  private final UserDao userDao;

  public AuthUseCase(UserDao userDao) {
    this.userDao = userDao;
  }

  public AuthResponse authenticate(AuthRequest authRequest) {
    authRequest.validateRequest();
    UserFilter userFilter = this.prepareFilter(authRequest);
    UserEntity userEntity =
        this.userDao
            .findOne(userFilter)
            .orElseThrow(() -> new AppException(UserExceptionManager.UserError.USER_NOT_FOUND));
    boolean isPasswordMatched = BCrypt.checkpw(authRequest.getPassword(), userEntity.getPassword());
    if (!isPasswordMatched)
      throw new AuthExceptionManager(AuthExceptionManager.AuthError.WRONG_CREDENTIALS);

    return this.getAuthResponse(userEntity);
  }

  private UserFilter prepareFilter(AuthRequest authRequest) {
    UserFilter userFilter = new UserFilter();
    userFilter.setEmail(authRequest.getEmail());
    return userFilter;
  }

  private AuthResponse getAuthResponse(UserEntity userEntity) {
    JWTTokenInfo jwtTokenInfo = this.getJWTTokenInfo(userEntity);
    String token = JWTTokenUtils.getJWTToken(jwtTokenInfo);
    String refreshToken = this.getRefreshToken(jwtTokenInfo);
    String fullName =
        userEntity
            .getFirstName()
            .concat(
                StringUtil.isNotBlankOrNull(userEntity.getMiddleName())
                    ? " " + userEntity.getMiddleName()
                    : "")
            .concat(" " + userEntity.getLastName());
    return new AuthResponse(token, refreshToken, userEntity.getRole(), fullName);
  }

  private JWTTokenInfo getJWTTokenInfo(UserEntity userEntity) {
    Map<String, Object> claimMap = new HashMap<>();
    claimMap.put("email", userEntity.getEmail());
    claimMap.put("role", userEntity.getRole());
    return new JWTTokenInfo.JWTTokenBuilder()
        .setClaim(claimMap)
        .setSubject(userEntity.getId())
        .build();
  }

  private String getRefreshToken(JWTTokenInfo jwtTokenInfo) {
    String refreshToken = "";
    try {
      refreshToken =
          JWTTokenUtils.getRefreshToken(
              jwtTokenInfo, true, NovaLmsAppSecurityEnvConstants.REFRESH_TOKEN_SECRET_KEY);
    } catch (Exception ex) {
      throw new AppException(
          HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Error generating refresh token");
    }
    return refreshToken;
  }
}
