package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Goal;
import com.learnwithted.kidbank.domain.UserProfile;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class GoalsCommand implements TransactionCommand {

  private final Account account;

  @Override
  public String execute(UserProfile userProfile) {
    Set<Goal> goals = account.goals();
    if (goals.isEmpty()) {
      return "Sorry, you don't have any goals.";
    }
    return goals.stream()
        .map(this::format)
        .collect(Collectors.joining());
  }

  private String format(Goal goal) {
    int balance = account.balance();
    return String.format("Your goal is %s, which is %s, and you have %s saved (%s to your goal) and you need %s more to buy it.",
                         goal.description(),
                         ScaledDecimals.formatAsMoney(goal.targetAmount()),
                         ScaledDecimals.formatAsMoney(balance),
                         goal.progressMadeWith(balance),
                         ScaledDecimals.formatAsMoney(goal.remainingAmount(balance)));
  }
}
