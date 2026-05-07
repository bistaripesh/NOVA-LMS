package com.inviq.dto.tenset.request.credential;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.util.validator.password.PasswordValidator;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 24/04/2026
 */
@Getter
@Setter
public class ChangeCredentialRequest extends BaseUpdateRequest {
  private String oldPassword;
  private String newPassword;
  private String confirmPassword;

  @Override
  public void validateRequest() {
    PasswordValidator.validatePasswordAndConfirmPasswordField(
        this.newPassword, this.confirmPassword);
    PasswordValidator.validatePassword(this.newPassword);
  }
}
