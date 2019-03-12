package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.StubBalanceChangeNotifier;
import com.learnwithted.kidbank.domain.Transaction;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class SpendCommandTest {

  @Test
  public void spendWithValidAmountShouldReduceAccountBalance() throws Exception {
    FakeTransactionRepository transactionRepository = new FakeTransactionRepository();
    transactionRepository.save(Transaction.createDeposit(LocalDateTime.now(), 100_00, "test"));
    Account account = new Account(transactionRepository,
                                  new StubBalanceChangeNotifier(),
                                  a -> { });

    SpendCommand spendCommand = new SpendCommand(account, 13_75);

    assertThat(spendCommand.execute(Role.PARENT))
        .isEqualTo("Spent $13.75, current balance is now $86.25");

    assertThat(account.balance())
        .isEqualTo(86_25);
  }

}