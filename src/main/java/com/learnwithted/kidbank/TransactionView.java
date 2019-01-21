package com.learnwithted.kidbank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionView {

  public static final DecimalFormat FORMAT_AS_DOLLARS_AND_CENTS = new DecimalFormat("$###,##0.00");

  private String date;
  private String action;
  private String amount;
  private String source;

  public static TransactionView from(Transaction txn) {
    String amountString = formatAsMoney(txn.amount());
    String dateAsString = formatAsDate(txn.dateTime());
    return new TransactionView(dateAsString, txn.action(), amountString, txn.source());
  }

  public static String formatAsDate(LocalDateTime localDateTime) {
    return DateTimeFormatter.ofPattern("MM/dd/yyyy").format(localDateTime);
  }

  public static String formatAsMoney(int amount) {
    BigDecimal amountInDollars = new BigDecimal(amount).scaleByPowerOfTen(-2);
    return FORMAT_AS_DOLLARS_AND_CENTS.format(amountInDollars);
  }
}
