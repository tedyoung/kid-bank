package com.learnwithted.kidbank;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionCommand {

  private String date;
  private String amount;
  private String description;

  public static TransactionCommand createWithTodayDate() {
    TransactionCommand transactionCommand = new TransactionCommand();
    LocalDate localDate = LocalDate.now();
    transactionCommand.setDate(localDate.format(DateFormatting.YYYY_MM_DD_DATE_FORMATTER));
    return transactionCommand;
  }

  public int amountInCents() {
    return ScaledDecimals.decimalToPennies(amount);
  }

  public LocalDateTime dateAsLocalDateTime() {
    return DateFormatting.toLocalDateTime(date);
  }
}
