package com.learnwithted.kidbank.domain;

public class StubBalanceChangeNotifier implements BalanceChangedNotifier {
  @Override
  public void balanceChanged(int amount, int balance) {
    // do nothing
  }
}
