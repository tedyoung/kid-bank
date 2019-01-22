package com.learnwithted.kidbank;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountControllerTest {

  @Test
  public void depositCommandShouldAddAmountToAccount() throws Exception {
    DepositCommand depositCommand = DepositCommand.createWithTodayDate();
    depositCommand.setAmount("12.34");

    Account account = new Account();

    AccountController accountController = new AccountController(account);

    accountController.deposit(depositCommand);

    assertThat(account.balance())
        .isEqualTo(1234);
  }

}