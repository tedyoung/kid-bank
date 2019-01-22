package com.learnwithted.kidbank;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DepositCommandTest {

  @Test
  public void decimalToPennies() {
    String amount = "4.95";
    assertThat(DepositCommand.decimalToPennies(amount))
        .isEqualTo(495);
  }

  @Test
  public void mdyyyStringShouldBeCorrectLocalDate() throws Exception {
    String rawDate = "7/8/2019";

    LocalDateTime localDateTime = DepositCommand.toLocalDateTime(rawDate);

    assertThat(localDateTime)
        .isEqualToIgnoringHours(LocalDateTime.of(2019, 7, 8, 0, 0));
  }

  @Test
  public void createWithTodayDateShouldHaveTodayDate() throws Exception {
    DepositCommand withTodayDate = DepositCommand.createWithTodayDate();

    assertThat(withTodayDate.dateAsLocalDateTime())
        .isEqualToIgnoringHours(LocalDateTime.now());
  }
}