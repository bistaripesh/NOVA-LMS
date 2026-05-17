package com.inviq.core.context;

import java.util.Collection;
import java.util.List;

import com.inviq.core.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author ripesh created on 23/04/2026
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

  private String userId;
  private String traceId;
  private String schema;
  private UserType userType;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public String getUsername() {
    return "";
  }
}
