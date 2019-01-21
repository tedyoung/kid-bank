package com.learnwithted.kidbank;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountControllerMoneyFormatTest {

  @Test
  public void format0AsMoneyResultsInDollarSignAnd2Decimals() {
    assertThat(AccountController.formatAsMoney(0))
        .isEqualTo("$0.00");
  }

  @Test
  public void formatLessThanOneDollarShouldHaveLeadingZero() throws Exception {
    assertThat(AccountController.formatAsMoney(99))
        .isEqualTo("$0.99");
  }

  @Test
  public void formatMoreThanOneDollarShouldHaveNoLeadingZeroes() throws Exception {
    assertThat(AccountController.formatAsMoney(123))
        .isEqualTo("$1.23");
  }

}