package com.learnwithted.kidbank.adapter;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountIntegrationTest {

  @Autowired
  Account account;

  @Test
  public void depositMoneyInYear2018ShouldResultInManyInterestCreditTransactions() throws Exception {
    account.deposit(localDateTimeAtMidnightOf(2018, 1, 30), 700_00, "initial deposit");

    // side-effect -- TODO: get rid of this when refactoring InterestEarningAccount & MonthlyInterestStrategy to a one-way dependency
    account.balance();

    long count = account.transactions().stream()
                        .filter(Transaction::isInterestCredit)
                        .count();

    assertThat(count)
        .isNotZero();
  }

}
