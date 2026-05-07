package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dto.tenset.request.auth.AuthRequest;
import com.inviq.dto.tenset.request.auth.JwtTokenRequest;
import com.inviq.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ripesh created on 23/04/2026
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping
  public ResponseEntity<RestResponseBody> authenticate(@RequestBody AuthRequest authRequest) {
    return RestResponse.ok(this.authService.authenticate(authRequest));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<RestResponseBody> authenticate(
      @RequestBody JwtTokenRequest jwtTokenRequest) {
    return RestResponse.ok(this.authService.getNewJwtToken(jwtTokenRequest));
  }
}
