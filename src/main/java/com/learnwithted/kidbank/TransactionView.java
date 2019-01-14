package com.learnwithted.kidbank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionView {
  private String date;
  private String action;
  private String amount;
  private String source;
}
