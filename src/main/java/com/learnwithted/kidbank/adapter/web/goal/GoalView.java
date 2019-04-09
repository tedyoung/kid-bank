package com.learnwithted.kidbank.adapter.web.goal;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Goal;
import lombok.Data;

@Data
public class GoalView {
  private final String description;
  private final String targetAmount;

  public static GoalView from(Goal goal) {
    String formattedTargetAmount = ScaledDecimals.formatAsMoney(goal.targetAmount());
    return new GoalView(goal.description(), formattedTargetAmount);
  }
}
