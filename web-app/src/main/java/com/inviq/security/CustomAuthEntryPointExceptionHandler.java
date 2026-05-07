package com.inviq.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.core.exception.ErrorData;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author ripesh created on 23/04/2026
 */
@Component
public class CustomAuthEntryPointExceptionHandler implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response
        .getWriter()
        .write(
            new ObjectMapper()
                .writeValueAsString(new ErrorData("Unauthorized", "Authentication required")));
  }
}
