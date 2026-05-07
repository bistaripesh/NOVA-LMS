package com.inviq.core.util;

import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ripesh created on 04/04/2025
 */
public class StringUtil {
  public static boolean isBlankOrNull(String str) {
    return str == null || StringUtils.isBlank(str);
  }

  public static boolean isNotBlankOrNull(String str) {
    return !isBlankOrNull(str);
  }

  public static String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public static String generateSqlWildCardOnBothSides(String str) {
    return "".concat("%").concat(str).concat("%");
  }
}
