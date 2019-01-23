package com.learnwithted.kidbank.adapter.web;

import java.math.BigDecimal;

public class ScaledDecimals {
  public static int decimalToPennies(String amount) {
    BigDecimal decimalAmount = new BigDecimal(amount);
    return decimalAmount.scaleByPowerOfTen(2).intValue();
  }
}
