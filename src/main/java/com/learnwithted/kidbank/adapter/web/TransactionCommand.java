package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.DateFormatting;
import com.learnwithted.kidbank.adapter.ScaledDecimals;
import lombok.Data;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionCommand {

  private String date;

  @Positive(message = "Amount must be more than $0.00")
  private BigDecimal amount;

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

  @PastOrPresent(message = "Date must be on or before Today")
  public LocalDateTime getDateAsLocalDateTime() {
    return DateFormatting.fromBrowserDate(date);
  }
}
