package com.learnwithted.kidbank.adapter.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatting {
  // the format for browsers <input> tag is YYYY-MM-DD -- dashes only! (not slash separators)
  public static final DateTimeFormatter YYYY_MM_DD_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  // the format that CSV file has when exported from Excel, MM/DD/YYYY, separated by slashes
  public static final DateTimeFormatter MM_DD_YYYY_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  public static LocalDateTime fromBrowserDate(String rawDate) {
    LocalDate localDate = LocalDate.parse(rawDate, YYYY_MM_DD_DATE_FORMATTER);
    return localDate.atStartOfDay(); // midnight on the above date
  }

  public static LocalDateTime fromCsvDate(String rawDate) {
    LocalDate localDate = LocalDate.parse(rawDate, MM_DD_YYYY_DATE_FORMATTER);
    return localDate.atStartOfDay();
  }

}
