package com.learnwithted.kidbank.adapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ScaledDecimals {
  private static final DecimalFormat FORMAT_AS_DOLLARS_AND_CENTS = new DecimalFormat("$###,##0.00");

  public static int decimalToPennies(String amount) {
    return decimalToPennies(new BigDecimal(amount));
  }

  public static int decimalToPennies(BigDecimal decimalAmount) {
    return decimalAmount.scaleByPowerOfTen(2).intValue();
  }

  public static String formatAsMoney(int amount) {
    BigDecimal amountInDollars = new BigDecimal(amount).scaleByPowerOfTen(-2);
    return FORMAT_AS_DOLLARS_AND_CENTS.format(amountInDollars);
  }
}
