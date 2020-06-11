package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Action;
import com.learnwithted.kidbank.domain.DummyUserProfile;
import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.UserProfile;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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

    TransactionView view = TransactionView.viewOf(transaction, 99_66);

    assertThat(view)
        .isEqualTo(new TransactionView("01/02/2013",
                                       "Interest Credit",
                                       "$98.76",
                                       "$99.66",
                                       "Interest Credit",
                                       "none"));
  }

  @Test
  public void runningBalanceReflectsSumOfBalancesUpToEachTransaction() throws Exception {
    Transaction transactionDeposit1 = Transaction.createDeposit(localDateTimeAtMidnightOf(2020, 6, 8),
                                                        12_34,
                                                        "source1",
                                                        new DummyUserProfile());
    Transaction transactionDeposit2 = Transaction.createDeposit(localDateTimeAtMidnightOf(2020, 6, 9),
                                                        45_67,
                                                        "source2",
                                                        new DummyUserProfile());
    Transaction transactionSpend = Transaction.createSpend(localDateTimeAtMidnightOf(2020, 6, 10),
                                                        36_27,
                                                        "source3",
                                                        new DummyUserProfile());

    List<Transaction> transactions = Arrays.asList(transactionDeposit1, transactionDeposit2, transactionSpend);

    List<TransactionView> transactionViews = TransactionView.viewsOf(transactions);

    assertThat(transactionViews) // assertJ no Hamcrest
        .extracting(TransactionView::getRunningBalance)
        .containsExactly(ScaledDecimals.formatAsMoney(12_34 + 45_67 - 36_27),
                         ScaledDecimals.formatAsMoney(12_34 + 45_67),
                         ScaledDecimals.formatAsMoney(12_34)
        );
  }

}
