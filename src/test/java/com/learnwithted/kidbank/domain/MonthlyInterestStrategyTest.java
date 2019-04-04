package com.learnwithted.kidbank.domain;

import org.junit.Test;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

public class MonthlyInterestStrategyTest {

  @Test
  public void noSpendOrDepositTransactionsResultsInNoInterestCreditTransactions() throws Exception {
    Clock today = TestClockSupport.createFixedClockOn(2019, 3, 5);
    MonthlyInterestStrategy interestStrategy = new MonthlyInterestStrategy(today);

    InterestEarningAccount account = TestAccountBuilder.builder().buildAsInterestEarning();

    // TODO -- future: do this "as of ... some date"
    interestStrategy.createInterestCreditTransactionsFor(account);
    assertThat(account.transactions())
        .isEmpty();
  }

  @Test
  public void openingDepositTransactionResultsInInterestCreditTransactionsThroughToday() throws Exception {
    Clock today = TestClockSupport.createFixedClockOn(2019, 3, 5);
    MonthlyInterestStrategy interestStrategy = new MonthlyInterestStrategy(today);

    InterestEarningAccount account = TestAccountBuilder.builder()
                                                       .initialBalanceOf(100_00, 2018, 12, 24)
                                                       .buildAsInterestEarning();

    interestStrategy.createInterestCreditTransactionsFor(account);

    List<Transaction> interestCredits = account.transactions().stream()
                                               .filter(Transaction::isInterestCredit)
                                               .collect(Collectors.toList());

    assertThat(interestCredits)
        .hasSize(3)
        .extracting("date")
        .containsExactlyInAnyOrder(localDateTimeAtMidnightOf(2019, 1, 1),
                                   localDateTimeAtMidnightOf(2019, 2, 1),
                                   localDateTimeAtMidnightOf(2019, 3, 1));
  }

}