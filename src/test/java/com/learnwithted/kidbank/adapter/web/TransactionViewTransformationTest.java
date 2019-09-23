package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Action;
import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.UserProfile;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionViewTransformationTest {

  @Test
  public void transactionShouldBeTransformedToView() throws Exception {
    UserProfile userProfile = new UserProfile("Dad", null, null, null);
    Transaction transaction = new Transaction(LocalDateTime.of(2012, 2, 9, 0, 0),
                                              Action.DEPOSIT,
                                              4598,
                                              "Gift",
                                              userProfile);

    TransactionView view = TransactionView.from(transaction);

    assertThat(view)
        .isEqualTo(new TransactionView("02/09/2012", "Deposit", "$45.98", "Gift", "Dad"));
  }

  @Test
  public void interestCreditTransactionHasNoneForCreator() throws Exception {
    Transaction transaction =
        Transaction.createInterestCredit(localDateTimeAtMidnightOf(2013, 1, 2),
                                         9876);

    TransactionView view = TransactionView.from(transaction);

    assertThat(view)
        .isEqualTo(new TransactionView("01/02/2013",
                                       "Interest Credit",
                                       "$98.76",
                                       "Interest Credit",
                                       "none"));
  }
}
