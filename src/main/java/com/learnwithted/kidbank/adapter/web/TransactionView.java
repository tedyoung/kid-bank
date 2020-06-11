package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.DateFormatting;
import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.UserProfile;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TransactionView {

  private final String date;
  private final String action;
  private final String amount;
  private final String runningBalance;
  private final String source;
  private final String creator;

  public TransactionView(String date, String action, String amount, String runningBalance, String source, String creator) {
    this.date = date;
    this.action = action;
    this.amount = amount;
    this.runningBalance = runningBalance;
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
    AtomicInteger runningBalance = new AtomicInteger(0);

    // we stream through these transactions from oldest to newest so that
    // accumulating the running balance works correctly
    List<TransactionView> views = transactions
        .stream()
        .peek(transaction -> runningBalance.addAndGet(transaction.signedAmount()))
        .map(txn -> viewOf(txn, runningBalance.get()))
        .collect(Collectors.toList());

    // now reverse the order as incoming transactions were ordered oldest-to-newest
    // and we want the display to be newest at the "top" and oldest at the "bottom"
    Collections.reverse(views);
    return views;
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

  public String getRunningBalance() {
    return runningBalance;
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
    if (!runningBalance.equals(that.runningBalance)) return false;
    if (!source.equals(that.source)) return false;
    return creator.equals(that.creator);
  }

  @Override
  public int hashCode() {
    int result = date.hashCode();
    result = 31 * result + action.hashCode();
    result = 31 * result + amount.hashCode();
    result = 31 * result + runningBalance.hashCode();
    result = 31 * result + source.hashCode();
    result = 31 * result + creator.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "TransactionView{" +
        "date='" + date + '\'' +
        ", action='" + action + '\'' +
        ", amount='" + amount + '\'' +
        ", runningBalance='" + runningBalance + '\'' +
        ", source='" + source + '\'' +
        ", creator='" + creator + '\'' +
        '}';
  }
}
