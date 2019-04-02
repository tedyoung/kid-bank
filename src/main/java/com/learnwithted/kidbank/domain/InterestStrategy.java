package com.learnwithted.kidbank.domain;

public interface InterestStrategy {
  void createInterestCreditTransactionsFor(InterestEarningAccount account);
}
