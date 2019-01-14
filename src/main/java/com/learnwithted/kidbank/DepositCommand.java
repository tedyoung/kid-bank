package com.learnwithted.kidbank;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositCommand {
  private String amount;

  public static int decimalToPennies(String amount) {
    BigDecimal decimalAmount = new BigDecimal(amount);
    return decimalAmount.scaleByPowerOfTen(2).intValue();
  }

  public int amountInCents() {
    return decimalToPennies(amount);
  }
}
