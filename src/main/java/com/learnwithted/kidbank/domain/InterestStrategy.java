package com.learnwithted.kidbank.domain;

public interface InterestStrategy {
  void creditInterestAsNeeded(InterestEarningAccount account);
}
