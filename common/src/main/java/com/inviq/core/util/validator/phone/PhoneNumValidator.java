package com.inviq.core.util.validator.phone;

import com.inviq.core.constants.NovaLmsRegexConstant;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.PhoneNumberExceptionManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ripesh created on 12/05/2026
 */
public class PhoneNumValidator {
  public static void validatePhoneNumber(String phoneNum) {
    Pattern pattern = Pattern.compile(NovaLmsRegexConstant.PHONE_REGEX);
    Matcher matcher = pattern.matcher(phoneNum);
    if (!matcher.matches())
      throw new AppException(PhoneNumberExceptionManager.PhoneNumberError.INVALID_PHONE_NUMBER);
  }
}
