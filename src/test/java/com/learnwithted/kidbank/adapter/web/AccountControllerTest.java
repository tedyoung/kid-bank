package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountControllerTest {

  @Test
  public void depositCommandShouldAddAmountToAccount() throws Exception {
    TransactionCommand depositCommand = TransactionCommand.createWithTodayDate();
    depositCommand.setAmount("12.34");

    Account account = new Account();

    AccountController accountController = new AccountController(account);

    accountController.deposit(depositCommand);

    assertThat(account.balance())
              .isEqualTo(1234);
  }

  @Test
  public void spendCommandShouldReduceAmountInAccount() throws Exception {
    TransactionCommand spendCommand = TransactionCommand.createWithTodayDate();
    spendCommand.setAmount("34.79");

    Account account = new Account();

    AccountController accountController = new AccountController(account);

    accountController.spend(spendCommand);

    assertThat(account.balance())
              .isEqualTo(-3479);
  }

}