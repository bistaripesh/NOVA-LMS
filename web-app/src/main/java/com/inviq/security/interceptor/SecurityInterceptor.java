package com.inviq.security.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.core.constants.NovaLmsAppSecurityEnvConstants;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.response.RestResponseBody;
import com.inviq.core.security.token.JWTTokenUtils;
import com.inviq.core.user.UserType;
import com.inviq.security.util.SecurityInterceptorUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author ripesh created on 23/04/2026
 */
@Component
public class SecurityInterceptor extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      filterChain.doFilter(request, response);
      return;
    }

    if (SecurityInterceptorUtil.isPublicEndpoint(request.getRequestURI())) {
      SecurityContextHolder.getContext()
          .setAuthentication(
              new UsernamePasswordAuthenticationToken(
                  new UserPrincipal("anonymous", "", "portal_users", UserType.ANONYMOUS),
                  null,
                  null));
      filterChain.doFilter(request, response);
      return;
    }

    String token = request.getHeader("Authorization");

    if (token == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response
          .getWriter()
          .write(
              new ObjectMapper().writeValueAsString(new RestResponseBody(false, "Invalid token")));
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8);
      return;
    }

    if ((token.startsWith("Bearer "))
        && !JWTTokenUtils.isValidJWTToken(
            token.replace("Bearer ", ""),
            false,
            NovaLmsAppSecurityEnvConstants.JWT_TOKEN_SECRET_KEY)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response
          .getWriter()
          .write(
              new ObjectMapper().writeValueAsString(new RestResponseBody(false, "Invalid token")));
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8);
      return;
    }

    Claims claims =
        JWTTokenUtils.getClaims(
            token.replace("Bearer ", ""),
            false,
            NovaLmsAppSecurityEnvConstants.JWT_TOKEN_SECRET_KEY);

    List<String> roles = List.of((String) claims.get("role"));

    List<SimpleGrantedAuthority> authorities =
        roles.stream().map(SimpleGrantedAuthority::new).toList();

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            new UserPrincipal(
                claims.getSubject(),
                "",
                "portal_users",
                UserType.getByCode(authorities.getFirst().getAuthority())),
            null,
            authorities);

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    filterChain.doFilter(request, response);
  }
}
