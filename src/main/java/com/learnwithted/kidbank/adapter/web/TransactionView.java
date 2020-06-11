package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.DateFormatting;
import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.UserProfile;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class TransactionView {

  private final String date;
  private final String action;
  private final String amount;
  private final String runningTotal;
  private final String source;
  private final String creator;

  public TransactionView(String date, String action, String amount, String runningTotal, String source, String creator) {
    this.date = date;
    this.action = action;
    this.amount = amount;
    this.runningTotal = runningTotal;
    this.source = source;
    this.creator = creator;
  }

  public static TransactionView viewOf(Transaction txn, int runningBalance) {
    String amountString = ScaledDecimals.formatAsMoney(txn.amount());
    String runningBalanceString = ScaledDecimals.formatAsMoney(runningBalance);
    String dateAsString = DateFormatting.formatAsDate(txn.dateTime());

    String actionString = ActionFormatter.format(txn.action());

    String creatorString = txn.creator()
                              .map(UserProfile::name)
                              .orElse("none");
    return new TransactionView(dateAsString, actionString, amountString,
                               runningBalanceString, txn.source(), creatorString);
  }

  public static List<TransactionView> viewsOf(List<Transaction> transactions) {
    int runningBalance = 0;
    return transactions
                  .stream()
                  .sorted(comparing(Transaction::dateTime).reversed())
                  .map(txn -> viewOf(txn, runningBalance))
                  .collect(Collectors.toList());
  }

  public String getDate() {
    return this.date;
  }

  public String getAction() {
    return this.action;
  }

  public String getAmount() {
    return this.amount;
  }

  public String getSource() {
    return this.source;
  }

  public String getCreator() {
    return this.creator;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TransactionView that = (TransactionView) o;

    if (!date.equals(that.date)) return false;
    if (!action.equals(that.action)) return false;
    if (!amount.equals(that.amount)) return false;
    if (!source.equals(that.source)) return false;
    return creator.equals(that.creator);
  }

  @Override
  public int hashCode() {
    int result = date.hashCode();
    result = 31 * result + action.hashCode();
    result = 31 * result + amount.hashCode();
    result = 31 * result + source.hashCode();
    result = 31 * result + creator.hashCode();
    return result;
  }

  public String toString() {
    return "TransactionView(date=" + this.getDate() + ", action=" + this.getAction() + ", amount=" + this.getAmount() + ", source=" + this.getSource() + ", creator=" + this.getCreator() + ")";
  }
}
