package com.learnwithted.kidbank;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountControllerTest {

  @Test
  public void format0AsMoneyResultsInDollarSignAnd2Decimals() {
    AccountController accountController = new AccountController();

    assertThat(accountController.formatAsMoney(0))
        .isEqualTo("$0.00");
  }

}