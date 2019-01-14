package com.learnwithted.kidbank;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountControllerTest {

  @Test
  public void format0AsMoneyResultsInDollarSignAnd2Decimals() {
    AccountController accountController = new AccountController(null);

    assertThat(accountController.formatAsMoney(0))
        .isEqualTo("$0.00");
  }

  @Test
  public void viewBalanceShouldDisplayBalanceOfGivenAccount() throws Exception {
    Account account = new Account();
    account.deposit(10);
    AccountController accountController = new AccountController(account);

    assertThat(accountController.viewBalance())
        .isEqualTo("$0.10");
  }

}