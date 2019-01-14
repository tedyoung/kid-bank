package com.learnwithted.kidbank;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DepositCommandTest {

  @Test
  public void decimalToPennies() {
    String amount = "4.95";
    assertThat(DepositCommand.decimalToPennies(amount))
        .isEqualTo(495);
  }
}