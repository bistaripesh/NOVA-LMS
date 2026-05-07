package com.inviq.util.validator.password;

import com.inviq.core.constants.NovaLmsAppConstants;
import com.inviq.core.exception.AppException;
import com.inviq.exception.AuthExceptionManager;
import com.inviq.exception.UserExceptionManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ripesh created on 24/04/2026
 */
public class PasswordValidator {

  public static void validatePassword(String password) {
    Pattern pattern = Pattern.compile(NovaLmsAppConstants.PASSWORD_VALIDATION_REGEX);
    Matcher matcher = pattern.matcher(password);
    if (!matcher.matches()) {
      throw new AppException(UserExceptionManager.UserError.PASSWORD_STANDARD_FAILED);
    }
  }

  public static void validatePasswordAndConfirmPasswordField(
      String password, String confirmPassword) {
    if (!password.equalsIgnoreCase(confirmPassword)) {
      throw new AppException(AuthExceptionManager.AuthError.PASSWORD_NOT_MATCHED);
    }
  }
}
