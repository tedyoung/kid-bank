package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DepositControllerTest {

  @Test
  public void depositCommandShouldAddAmountToAccount() throws Exception {
    TransactionCommand depositCommand = TransactionCommand.createWithTodayDate();
    depositCommand.setAmount("12.34");

    Account account = new Account(new FakeTransactionRepository());

    DepositController depositController = new DepositController(account);

    depositController.processDepositCommand(depositCommand);

    assertThat(account.balance())
        .isEqualTo(1234);
  }

}
