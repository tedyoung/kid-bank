package com.learnwithted.kidbank;

import java.math.BigDecimal;

public class ScaledDecimals {
  public static int decimalToPennies(String amount) {
    BigDecimal decimalAmount = new BigDecimal(amount);
    return decimalAmount.scaleByPowerOfTen(2).intValue();
  }
}
