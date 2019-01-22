package com.learnwithted.kidbank;

import lombok.Data;

@Data
public class SpendCommand {
  private String date;
  private String amount;
  private String description;


}
