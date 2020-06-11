package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Action;
import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.UserProfile;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.*;

public class TransactionViewTransformationTest {

  @Test
  public void transactionShouldBeTransformedToView() throws Exception {
    UserProfile userProfile = new UserProfile("Dad", null, null, null);
    Transaction transaction = new Transaction(LocalDateTime.of(2012, 2, 9, 0, 0),
                                              Action.DEPOSIT,
                                              45_98,
                                              "Gift",
                                              userProfile);

    TransactionView view = TransactionView.viewOf(transaction, 49_90);

    assertThat(view)
        .isEqualTo(new TransactionView("02/09/2012", "Deposit", "$45.98", "$49.90", "Gift", "Dad"));
  }

  @Test
  public void interestCreditTransactionHasNoneForCreator() throws Exception {
    Transaction transaction =
        Transaction.createInterestCredit(localDateTimeAtMidnightOf(2013, 1, 2),
                                         98_76);

    TransactionView view = TransactionView.viewOf(transaction, 98_76);

    assertThat(view)
        .isEqualTo(new TransactionView("01/02/2013",
                                       "Interest Credit",
                                       "$98.76",
                                       "$98.76",
                                       "Interest Credit",
                                       "none"));
  }
}
