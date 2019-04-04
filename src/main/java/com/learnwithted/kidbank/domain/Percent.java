package com.learnwithted.kidbank.domain;

public class Percent {
  private final int numerator;
  private final int denominator;

  private Percent(int numerator, int denominator) {
    this.numerator = numerator;
    this.denominator = denominator;
  }

  public static PercentBuilder of(int numerator) {
    return new PercentBuilder(numerator);
  }

  @Override
  public String toString() {
    return roundToInteger() + "%";
  }

  public int roundToInteger() {
    if (numerator >= denominator) {
      return 100;
    }

    double rawPercent = 100.0 * numerator / denominator;
    int roundedPercent = (int) (rawPercent + .5);

    return roundedPercent < 100 ? roundedPercent : 99;
  }

  public static class PercentBuilder {
    private final int numerator;

    PercentBuilder(int numerator) {
      this.numerator = numerator;
    }

    public Percent over(int denominator) {
      return new Percent(numerator, denominator);
    }
  }
}
