package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SpendControllerTest {

  @Test
  public void spendCommandShouldReduceAmountInAccount() throws Exception {
    TransactionCommand spendCommand = TransactionCommand.createWithTodayDate();
    spendCommand.setAmount("34.79");

    Account account = new Account(new FakeTransactionRepository());

    SpendController spendController = new SpendController(account);

    spendController.processSpendCommand(spendCommand);

    assertThat(account.balance())
        .isEqualTo(-3479);
  }


}
