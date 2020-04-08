package br.tulli.jm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {
  private final static String DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

  public static String dateTimeFormat() {
    return dateTimeFormat(null, null);
  }

  public static String dateTimeFormat(String format, Date date) {
    if (format == null) {
      format = DEFAULT_DATE_TIME_FORMAT;
    }

    if (date == null) {
      date = getCurrentTime();
    }

    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(date);
  }

  public static Date getCurrentTime() {
    return GregorianCalendar.getInstance().getTime();
  }
}
