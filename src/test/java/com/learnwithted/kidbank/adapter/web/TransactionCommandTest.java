package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.DateFormatting;
import com.learnwithted.kidbank.adapter.ScaledDecimals;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionCommandTest {

  @Test
  public void decimalToPennies() {
    String amount = "4.95";
    assertThat(ScaledDecimals.decimalToPennies(amount))
        .isEqualTo(495);
  }

  @Test
  public void yyyy_MM_dd_StringShouldBeCorrectLocalDate() throws Exception {
    String rawDate = "2019-07-08";

    LocalDateTime localDateTime = DateFormatting.fromBrowserDate(rawDate);

    assertThat(localDateTime)
        .isEqualToIgnoringHours(LocalDateTime.of(2019, 7, 8, 0, 0));
  }

  @Test
  public void createWithTodayDateShouldHaveTodayDate() throws Exception {
    TransactionCommand withTodayDate = TransactionCommand.createWithTodayDate();

    assertThat(withTodayDate.getDateAsLocalDateTime())
        .isEqualToIgnoringHours(LocalDateTime.now());
  }
}