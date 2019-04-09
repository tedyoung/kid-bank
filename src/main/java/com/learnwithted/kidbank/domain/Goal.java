package com.learnwithted.kidbank.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Goal {
  private final String description;
  private final int targetAmount;

  // EXTRINSIC property used by Repository
  @Getter
  @Setter
  private Long id;

  public Goal(String description, int targetAmount) {
    this.description = description;
    this.targetAmount = targetAmount;
  }

  public String description() {
    return description;
  }

  public int targetAmount() {
    return targetAmount;
  }

  public Percent progressMadeWith(int amount) {
    return Percent.of(amount).over(targetAmount);
  }


  public int remainingAmount(int amount) {
    return Math.max(targetAmount - amount, 0);
  }

}
