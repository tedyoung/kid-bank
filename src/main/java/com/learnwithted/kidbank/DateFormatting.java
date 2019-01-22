package com.learnwithted.kidbank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatting {
  // the format for browsers <input> tag is YYYY-MM-DD -- dashes only! (not slash separators)
  public static final DateTimeFormatter YYYY_MM_DD_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static LocalDateTime toLocalDateTime(String rawDate) {
    LocalDate localDate = LocalDate.parse(rawDate, YYYY_MM_DD_DATE_FORMATTER);
    return localDate.atStartOfDay(); // midnight on the above date
  }
}
