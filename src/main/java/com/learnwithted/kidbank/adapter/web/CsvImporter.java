package com.learnwithted.kidbank.adapter.web;

import com.google.common.base.Splitter;
import com.learnwithted.kidbank.domain.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CsvImporter {

  private static Transaction transactionFromCsv(String csvRow) {
    List<String> csvCells = parseCsvRow(csvRow);

    LocalDateTime localDateTime = DateFormatting.fromCsvDate(csvCells.get(0));
    String transactionType = csvCells.get(1);
    int amount = dollarStringToScaledInt(csvCells.get(2));
    String description = csvCells.get(3);

    return createTransactionFrom(localDateTime, transactionType, amount, description);
  }

  private static Transaction createTransactionFrom(LocalDateTime localDateTime,
      String transactionType, int amount, String description) {

    Transaction transaction;
    switch (transactionType) {
      case "Cash Deposit":
      case "Interest Credit":
        transaction = Transaction.createDeposit(localDateTime, amount, description);
        break;
      case "Payment":
        transaction = Transaction.createSpend(localDateTime, amount, description);
        break;
      default:
        throw new IllegalArgumentException("Unknown transaction type: " + transactionType);
    }
    return transaction;
  }

  private static int dollarStringToScaledInt(String dollarString) {
    return Integer.parseInt(dollarString.replaceAll("[$,.()]", ""));
  }

  private static List<String> parseCsvRow(String csvRow) {
    return Splitter.on(',')
                   .trimResults()
                   .splitToList(csvRow);
  }

  public List<Transaction> importFrom(List<String> csvList) {
    return csvList.stream()
                  .map(CsvImporter::transactionFromCsv)
                  .collect(Collectors.toList());
  }


}
