package com.learnwithted.kidbank.table;

import com.google.common.base.Splitter;
import com.learnwithted.kidbank.adapter.web.CsvImporter;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import com.learnwithted.kidbank.domain.Transaction;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvAssertion {
  private static final Pattern NEW_LINE_SEPARATOR_PATTERN = Pattern.compile("\n");
  private final CsvImporter csvImporter = new CsvImporter();
  private final Account account;
  private String given = null;
  private String when = null;

  private CsvAssertion(int year, int month, int day) {
    account = TestAccountBuilder.builder()
                                .buildAsInterestEarning(year, month, day);
  }

  public static CsvAssertion on(int year, int month, int day) {
    return new CsvAssertion(year, month, day);
  }

  public void then(String then) {
    assertThat(given)
        .describedAs("Given has not been defined.")
        .isNotNull();

    assertThat(when)
        .describedAs("When has not been defined.")
        .isNotNull();

    process(given);

    process(when);

    List<Transaction> thenTransactions = transactionsFromCsv(then);

    assertThat(account.transactions())
        .usingElementComparatorIgnoringFields("id")
        .containsExactlyInAnyOrderElementsOf(thenTransactions);
  }

  private void process(String given) {
    List<Transaction> givenTransactions = transactionsFromCsv(given);
    account.load(givenTransactions);
  }

  private List<Transaction> transactionsFromCsv(String then) {
    return csvImporter.importFrom(toLines(then));
  }

  private List<String> toLines(String content) {
    return Splitter.on(NEW_LINE_SEPARATOR_PATTERN)
                   .omitEmptyStrings()
                   .splitToList(content);
  }

  public CsvAssertion given(String given) {
    this.given = given;
    return this;
  }

  public CsvAssertion when(String when) {
    this.when = when;
    return this;
  }

}
