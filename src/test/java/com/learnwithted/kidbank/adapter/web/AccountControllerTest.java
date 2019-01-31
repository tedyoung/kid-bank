package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Transaction;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountControllerTest {

  @Test
  public void depositCommandShouldAddAmountToAccount() throws Exception {
    TransactionCommand depositCommand = TransactionCommand.createWithTodayDate();
    depositCommand.setAmount("12.34");

    Account account = new Account(new FakeTransactionRepository());

    AccountController accountController = new AccountController(account);

    accountController.deposit(depositCommand);

    assertThat(account.balance())
        .isEqualTo(1234);
  }

  @Test
  public void spendCommandShouldReduceAmountInAccount() throws Exception {
    TransactionCommand spendCommand = TransactionCommand.createWithTodayDate();
    spendCommand.setAmount("34.79");

    Account account = new Account(new FakeTransactionRepository());

    AccountController accountController = new AccountController(account);

    accountController.spend(spendCommand);

    assertThat(account.balance())
        .isEqualTo(-3479);
  }

  @Test
  public void importCommandShouldLoadTransactionsIntoAccount() throws Exception {
    ImportCommand importCommand = new ImportCommand();
    importCommand.setContent("03/07/2018,Cash Deposit, $5.50 ,Bottle return\r\n" +
                                 "03/25/2018,Payment, $(12.00),MTG Draft Game Kastle\r\n" +
                                 "04/01/2018,Interest Credit, $0.11 ,Interest based on 2%/year\r\n");

    Account account = new Account(new FakeTransactionRepository());

    AccountController accountController = new AccountController(account);
    accountController.importCsv(importCommand);

    assertThat(account.transactions())
        .containsExactlyInAnyOrder(
            Transaction.createDeposit(LocalDateTime.of(2018, 3, 7, 0, 0),
                                      550, "Bottle return"),
            Transaction.createSpend(LocalDateTime.of(2018, 3, 25, 0, 0),
                                    1200, "MTG Draft Game Kastle"),
            Transaction.createInterestCredit(LocalDateTime.of(2018, 4, 1, 0, 0), 11));
  }

}