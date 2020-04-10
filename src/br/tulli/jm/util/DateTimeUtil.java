package br.tulli.jm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {
  private final static String DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

  public static String dateTimeFormat() {
    return dateTimeFormat(DEFAULT_DATE_TIME_FORMAT, getCurrentTime());
  }

  public static String dateTimeFormat(String format, Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(date);
  }

  public static Date getCurrentTime() {
    return GregorianCalendar.getInstance().getTime();
  }
}
