package com.inviq.dto.tenset.request.user;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.core.util.validator.email.EmailValidator;
import com.inviq.exception.UserExceptionManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 18/04/2025
 */
@Getter
@Setter
public class UserUpdateRequest extends BaseUpdateRequest {
  private String firstName;
  private String middleName;
  private String lastName;
  private String email;

  @Override
  public void validateRequest() {
    EmailValidator.validateEmail(this.email);
    if (StringUtil.isBlankOrNull(this.firstName))
      throw new AppException(UserExceptionManager.UserError.USER_FIRST_NAME_REQUIRED);
    if (StringUtil.isBlankOrNull(this.lastName))
      throw new AppException(UserExceptionManager.UserError.USER_LAST_NAME_REQUIRED);
  }
}
