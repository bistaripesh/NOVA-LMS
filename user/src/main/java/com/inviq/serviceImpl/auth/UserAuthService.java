package com.inviq.serviceImpl.auth;

import com.inviq.dao.UserDao;
import com.inviq.dto.tenset.request.auth.AuthRequest;
import com.inviq.dto.tenset.request.auth.AuthResponse;
import com.inviq.dto.tenset.request.auth.JwtTokenRequest;
import com.inviq.service.AuthService;
import com.inviq.serviceImpl.auth.usecase.AuthUseCase;
import com.inviq.serviceImpl.auth.usecase.NewJwtTokenUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 23/04/2026
 */
@Service
public class UserAuthService implements AuthService {

  private final UserDao userDao;

  @Autowired
  public UserAuthService(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public AuthResponse authenticate(AuthRequest authRequest) {
    return new AuthUseCase(this.userDao).authenticate(authRequest);
  }

  @Override
  public AuthResponse getNewJwtToken(JwtTokenRequest jwtTokenRequest) {
    return new NewJwtTokenUseCase().execute(jwtTokenRequest);
  }
}
