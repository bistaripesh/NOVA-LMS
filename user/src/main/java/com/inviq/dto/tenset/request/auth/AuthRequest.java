package com.inviq.dto.tenset.request.auth;

import com.inviq.core.dto.request.crud.Request;
import com.inviq.core.util.validator.email.EmailValidator;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 23/04/2026
 */
@Getter
@Setter
public class AuthRequest implements Request {
  private String email;
  private String password;

  @Override
  public void validateRequest() {
    EmailValidator.validateEmail(this.email);
  }
}
