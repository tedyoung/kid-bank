package com.learnwithted.kidbank.adapter.web.goal;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Goal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CreateGoal {
  private String description;
  @Positive(message = "Your goal amount must be more than $0.00")
  private BigDecimal targetAmount;

  Goal asGoal() {
    return new Goal(description, ScaledDecimals.decimalToPennies(targetAmount));
  }
}
