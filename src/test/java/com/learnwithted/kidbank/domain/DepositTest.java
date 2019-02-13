package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.adapter.web.FakeTransactionRepository;
import org.junit.Test;

import static com.learnwithted.kidbank.domain.TestClockSupport.createFixedClockOn;
import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

public class DepositTest {

  private static final FakeTransactionRepository FAKE_TRANSACTION_REPOSITORY = new FakeTransactionRepository();
  private static final InterestStrategy DO_NOTHING_INTEREST_STRATEGY = account -> { };

  @Test
  public void deposit495ShouldResultInBalanceOf495() throws Exception {
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY,
                                  createFixedClockOn(2019, 2, 27),
                                  DO_NOTHING_INTEREST_STRATEGY);

    account.deposit(
        localDateTimeAtMidnightOf(2018, 12, 27),
        495, "Bottle Return");

    assertThat(account.balance())
        .isEqualTo(495);
  }

  @Test
  public void deposit100Then200ShouldResultIn300Balance() throws Exception {
    Account account = new Account(FAKE_TRANSACTION_REPOSITORY,
                                  createFixedClockOn(2019, 3, 2),
                                  DO_NOTHING_INTEREST_STRATEGY);

    account.deposit(localDateTimeAtMidnightOf(2019, 2, 13), 100, "Bottle Return");
    account.deposit(localDateTimeAtMidnightOf(2019, 2, 13), 200, "Bottle Return");

    assertThat(account.balance())
        .isEqualTo(300);
  }
}
