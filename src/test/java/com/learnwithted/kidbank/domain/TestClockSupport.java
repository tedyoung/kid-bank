package com.learnwithted.kidbank.domain;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TestClockSupport {
  public static Clock createFixedClockOn(int year, int month, int day) {
    LocalDateTime dateTime = localDateTimeAtMidnightOf(year, month, day);
    Instant instant = Instant.from(dateTime.atZone(ZoneId.systemDefault()));
    return Clock.fixed(instant, ZoneId.systemDefault());
  }

  public static LocalDateTime localDateTimeAtMidnightOf(int year, int month, int day) {
    return LocalDateTime.of(year, month, day, 0, 0);
  }
}
