package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.DateFormatting;
import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.UserProfile;
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
  private String creator;

  public static TransactionView from(Transaction txn) {
    String amountString = ScaledDecimals.formatAsMoney(txn.amount());
    String dateAsString = DateFormatting.formatAsDate(txn.dateTime());

    String actionString = ActionFormatter.format(txn.action());

    String creatorString = txn.creator()
                              .map(UserProfile::name)
                              .orElse("none");
    return new TransactionView(dateAsString, actionString, amountString, txn.source(), creatorString);
  }

}
