package com.learnwithted.kidbank.domain;

import org.junit.Ignore;
import org.junit.Test;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore("During refactoring")
public class InterestCreditRecalculationTest {

  @Test
  public void insertingOlderTransactionRecalculatesInterestCreditsAfterInsertedTransaction() throws Exception {
    //    Given today is 6/5/2018, with initial balance of $500
    TestAccountBuilder testAccountBuilder =
        TestAccountBuilder.builder()
                          .clockOf(2018, 6, 5)
                          .initialBalanceOf(500_00, 2018, 4, 15)
                          .withMonthlyInterestStrategy();
    Account account = testAccountBuilder.build();

    account.deposit(localDateTimeAtMidnightOf(2018, 4, 25), 40_00, "deposit");
    account.interestCredit(localDateTimeAtMidnightOf(2018, 5, 1), 1_13);
    account.deposit(localDateTimeAtMidnightOf(2018, 5, 3), 60_00, "deposit");
    account.interestCredit(localDateTimeAtMidnightOf(2018, 6, 1), 1_25);

    //    When we add old transaction
    account.spend(localDateTimeAtMidnightOf(2018, 5, 12), 75_00, "spend");

    //    And when we calculate interest
    InterestStrategy interestStrategy = testAccountBuilder.interestStrategy();
    interestStrategy.creditInterestAsNeeded(account);


    //    Then there are 7 transactions: an interest reversal, and a new interest credit
    assertThat(account.transactions())
        .hasSize(7);
  }


}
