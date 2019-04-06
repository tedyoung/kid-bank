package com.learnwithted.kidbank.table;

import org.junit.Test;

public class SimpleTableTest {

  @Test
  public void singleGivenParsedIntoCorrectTransactionMatchesThen() throws Exception {

    CsvAssertion.on(2018, 3, 5)
                .given("03/05/2018,Cash Deposit,35")
                .when("")
                .then("03/05/2018,Cash Deposit,35");
  }

  @Test
  public void retroactiveTransactionUpdatesInterestCredits() throws Exception {
    String given = "04/05/2018,Cash Deposit,500\n" +
                       "04/25/2018,Cash Deposit,40\n" +
                       "05/01/2018,Interest Credit,1.13\n" +
                       "05/03/2018,Cash Deposit,60\n" +
                       "06/01/2018,Interest Credit,1.25";

    String when = "05/12/2018,Payment,-75";

    String then = "04/05/2018,Cash Deposit,500\n" +
                      "04/25/2018,Cash Deposit,40\n" +
                      "05/01/2018,Interest Credit,1.13\n" +
                      "05/03/2018,Cash Deposit,60\n" +
                      "05/12/2018,Payment,-75\n" +
                      "06/01/2018,Interest Credit,1.1";

    CsvAssertion.on(2018, 6, 5)
                .given(given)
                .when(when)
                .then(then);
  }

}
