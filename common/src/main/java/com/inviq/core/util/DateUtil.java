package com.inviq.core.util;

import com.inviq.core.constants.AppDateConstants;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author ripesh created on 20/04/2025
 */
public class DateUtil {

  public static String getCurrentDateTimeByFormat(String format) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    LocalDateTime dateTime = LocalDateTime.now();
    return dateTime.format(dateTimeFormatter);
  }

  public static Date stringToDate(String stringDate) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(AppDateConstants.DATE_FORMAT);
      return formatter.parse(stringDate);

    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
