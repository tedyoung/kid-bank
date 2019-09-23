package com.learnwithted.kidbank.adapter.web;

import com.google.common.base.Splitter;
import com.learnwithted.kidbank.adapter.DateFormatting;
import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.learnwithted.kidbank.domain.Action.DEPOSIT;
import static com.learnwithted.kidbank.domain.Action.SPEND;

public class CsvImporter {

  private static Transaction createTransactionFrom(LocalDateTime localDateTime,
      String transactionType, int amount, String description) {

    Transaction transaction;
    switch (transactionType) {
      case "Cash Deposit":
      case "Deposit":
        transaction = createDeposit(localDateTime, amount, description);
        break;
      case "Payment":
        transaction = createSpend(localDateTime, amount, description);
        break;
      case "Interest Credit":
        transaction = Transaction.createInterestCredit(localDateTime, amount);
        break;
      default:
        throw new IllegalArgumentException("During CSV Import, transaction type: '" + transactionType + "' was not expected for transaction dated " + localDateTime);
    }
    return transaction;
  }

  private static Transaction transactionFromCsv(String csvRow) {
    List<String> csvCells = parseCsvRow(csvRow);

    LocalDateTime localDateTime = DateFormatting.fromCsvDate(csvCells.get(0));
    String transactionType = csvCells.get(1);
    int amount = dollarStringToScaledInt(csvCells.get(2));
    String description;
    if (csvCells.size() > 3) {
      description = csvCells.get(3);
    } else {
      description = "";
    }

    return createTransactionFrom(localDateTime, transactionType, amount, description);
  }

  static Transaction createDeposit(LocalDateTime localDateTime, int amount, String source) {
    return new Transaction(localDateTime, DEPOSIT, amount, source);
  }

  private static int dollarStringToScaledInt(String dollarString) {
    // remove unneeded characters, including the minus sign
    // (we determine sign based on transaction type, i.e., spend or deposit)
    String cleanedAmount = dollarString.replaceAll("[$,()\\-]", "");
    return ScaledDecimals.decimalToPennies(cleanedAmount);
  }

  private static List<String> parseCsvRow(String csvRow) {
    return Splitter.on(',')
                   .trimResults()
                   .splitToList(csvRow);
  }

  static Transaction createSpend(LocalDateTime localDateTime, int amount, String source) {
    return new Transaction(localDateTime, SPEND, amount, source);
  }

  public List<Transaction> importFrom(List<String> csvList) {
    return csvList.stream()
                  .map(CsvImporter::transactionFromCsv)
                  .collect(Collectors.toList());
  }


}
