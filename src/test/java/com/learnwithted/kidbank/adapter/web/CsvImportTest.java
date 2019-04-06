package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Transaction;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.learnwithted.kidbank.domain.TestClockSupport.localDateTimeAtMidnightOf;
import static org.assertj.core.api.Assertions.assertThat;

public class CsvImportTest {

  @Test
  public void singleRowDepositShouldResultInSingleDepositTransaction() throws Exception {

    String csv = "01/05/2018,Cash Deposit, $50.00 ,Birthday gift";
    List<String> csvList = Lists.list(csv);

    List<Transaction> transactions = new CsvImporter().importFrom(csvList);

    assertThat(transactions)
        .usingElementComparatorIgnoringFields("id")
        .containsExactly(
            Transaction.createDeposit(LocalDateTime.of(2018, 1, 5, 0, 0),
                                      5000, "Birthday gift"));
  }

  @Test
  public void depositWithNoDecimalForAmountIsTreatedAsDollars() throws Exception {
    String csv = "02/03/2018,Cash Deposit,40,Birthday gift";
    List<String> csvList = Lists.list(csv);

    List<Transaction> transactions = new CsvImporter().importFrom(csvList);

    assertThat(transactions)
        .usingElementComparatorIgnoringFields("id")
        .containsExactly(
            Transaction.createDeposit(localDateTimeAtMidnightOf(2018, 2, 3),
                                      40_00, "Birthday gift"));
  }

  @Test
  public void depositWithNoDescriptionCreatesDepositTransaction() throws Exception {
    String csv = "02/09/2018,Cash Deposit,75";
    List<String> csvList = Lists.list(csv);

    List<Transaction> transactions = new CsvImporter().importFrom(csvList);

    assertThat(transactions)
        .usingElementComparatorIgnoringFields("id")
        .containsExactly(
            Transaction.createDeposit(localDateTimeAtMidnightOf(2018, 2, 9),
                                      75_00, ""));
  }

  @Test
  public void spendWithMinusSignForAmountIsIgnored() throws Exception {
    List<String> csvList = Lists.list("05/12/2018,Payment,-57");

    List<Transaction> transactions = new CsvImporter().importFrom(csvList);

    assertThat(transactions)
        .usingElementComparatorIgnoringFields("id")
        .containsExactly(
            Transaction.createSpend(localDateTimeAtMidnightOf(2018, 5, 12),
                                    57_00, ""));
  }

  @Test
  public void multipleDepositRowsShouldResultInMultipleDepositTransactions() throws Exception {
    List<String> csvList = Lists.list(
        "05/03/2018,Cash Deposit, $6.75 ,Bottle return",
        "05/24/2018,Deposit, $7.75 ,Bottle return");

    List<Transaction> transactions = new CsvImporter().importFrom(csvList);

    assertThat(transactions)
        .usingElementComparatorIgnoringFields("id")
        .containsExactly(
            Transaction.createDeposit(LocalDateTime.of(2018, 5, 3, 0, 0),
                                      675, "Bottle return"),
            Transaction.createDeposit(LocalDateTime.of(2018, 5, 24, 0, 0),
                                      775, "Bottle return"));
  }

  @Test
  public void singleRowInterestCreditShouldResultInSingleInterestCreditTransaction() throws Exception {

    String csv = "02/01/2018,Interest Credit, $0.08 ,Interest based on 2%/year";
    List<String> csvList = Lists.list(csv);

    List<Transaction> transactions = new CsvImporter().importFrom(csvList);

    assertThat(transactions)
        .usingElementComparatorIgnoringFields("id")
        .containsExactly(
            Transaction.createInterestCredit(LocalDateTime.of(2018, 2, 1, 0, 0), 8));
  }

  @Test
  public void singleRowPaymentShouldResultInSingleSpendTransaction() throws Exception {

    String csv = "01/04/2019,Payment, $(30.00),Share of Corsair headphones";
    List<String> csvList = Lists.list(csv);

    List<Transaction> transactions = new CsvImporter().importFrom(csvList);

    assertThat(transactions)
        .usingElementComparatorIgnoringFields("id")
        .containsExactly(
            Transaction.createSpend(LocalDateTime.of(2019, 1, 4, 0, 0),
                                    3000, "Share of Corsair headphones"));

  }

}