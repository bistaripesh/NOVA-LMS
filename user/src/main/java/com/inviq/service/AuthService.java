package com.inviq.service;

import com.inviq.dto.tenset.request.auth.AuthRequest;
import com.inviq.dto.tenset.request.auth.AuthResponse;
import com.inviq.dto.tenset.request.auth.JwtTokenRequest;

/**
 * @author ripesh created on 23/04/2026
 */
public interface AuthService {
  AuthResponse authenticate(AuthRequest authRequest);

  AuthResponse getNewJwtToken(JwtTokenRequest jwtTokenRequest);
}
