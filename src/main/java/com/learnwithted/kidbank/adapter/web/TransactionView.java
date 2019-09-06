package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.DateFormatting;
import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionView {

  private String date;
  private String action;
  private String amount;
  private String source;

  public static TransactionView from(Transaction txn) {
    String amountString = ScaledDecimals.formatAsMoney(txn.amount());
    String dateAsString = DateFormatting.formatAsDate(txn.dateTime());

    String actionString = ActionFormatter.format(txn.action());

    return new TransactionView(dateAsString, actionString, amountString, txn.source());
  }

}
