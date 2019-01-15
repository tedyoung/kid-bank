package com.learnwithted.kidbank;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountControllerTest {

  @Test
  public void format0AsMoneyResultsInDollarSignAnd2Decimals() {
    AccountController accountController = new AccountController(null);

    assertThat(accountController.formatAsMoney(0))
        .isEqualTo("$0.00");
  }

  @Test
  public void formatLessThanOneDollarShouldHaveLeadingZero() throws Exception {
    AccountController accountController = new AccountController(null);

    assertThat(accountController.formatAsMoney(99))
        .isEqualTo("$0.99");
  }

  @Test
  public void formatMoreThanOneDollarShouldHaveNoLeadingZeroes() throws Exception {
    AccountController accountController = new AccountController(null);

    assertThat(accountController.formatAsMoney(123))
        .isEqualTo("$1.23");
  }

  @Test
  public void depositCommandShouldAddAmountToAccount() throws Exception {
    DepositCommand depositCommand = new DepositCommand();
    depositCommand.setAmount("12.34");

    Account account = new Account();

    AccountController accountController = new AccountController(account);

    accountController.deposit(depositCommand);

    assertThat(account.balance())
        .isEqualTo(1234);
  }

  @Test
  public void viewBalanceShouldDisplayBalanceOfGivenAccount() throws Exception {
    Account account = new Account();
    account.deposit(LocalDateTime.now(), 10, "doesn't matter");
    AccountController accountController = new AccountController(account);

    assertThat(accountController.viewBalance())
        .isEqualTo("$0.10");
  }

}