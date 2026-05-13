package com.inviq.core.util.validator.email;

import com.inviq.core.constants.NovaLmsRegexConstant;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.EmailExceptionManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ripesh created on 24/04/2026
 */
public class EmailValidator {
  public static void validateEmail(String email) {
    Pattern pattern = Pattern.compile(NovaLmsRegexConstant.EMAIL_VALIDATION_REGEX);
    Matcher matcher = pattern.matcher(email);
    if (!matcher.matches())
      throw new AppException(EmailExceptionManager.EmailValidationError.INVALID_EMAIL);
  }
}
