package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GoalProgressTest {

  @Test
  public void noProgressMadeIsZeroPercentProgress() throws Exception {
    Goal goal = new Goal("goal", 100);

    Percent percent = goal.progressMadeWith(0);

    assertThat(percent.roundToInteger())
        .isZero();
  }

  @Test
  public void goalIsHalfwayReturns50PercentProgress() throws Exception {
    Goal goal = new Goal("goal", 10_00);

    Percent percent = goal.progressMadeWith(5_00);

    assertThat(percent.roundToInteger())
        .isEqualTo(50);
  }

  @Test
  public void goalIsAlmostHalfwayRoundsTo50PercentProgress() throws Exception {
    Goal goal = new Goal("goal", 10_00);

    Percent percent = goal.progressMadeWith(4_95);

    assertThat(percent.roundToInteger())
        .isEqualTo(50);
  }

  @Test
  public void goalIsJustLessThanHalfwayRoundsTo49PercentProgress() throws Exception {
    Goal goal = new Goal("goal", 10_00);

    Percent percent = goal.progressMadeWith(4_94);

    assertThat(percent.roundToInteger())
        .isEqualTo(49);
  }

  @Test
  public void percentStaysAt99UntilGoalIsFullyMet() throws Exception {
    Goal goal = new Goal("goal", 10_00);

    Percent percent = goal.progressMadeWith(9_99); // so close!

    assertThat(percent.roundToInteger())
        .isEqualTo(99);
  }

  @Test
  public void goalIsMetReturns100PercentProgress() throws Exception {
    Goal goal = new Goal("goal", 10_00);

    Percent percent = goal.progressMadeWith(10_00);

    assertThat(percent.roundToInteger())
        .isEqualTo(100);
  }

  @Test
  public void goalIsExceededStillReturns100PercentProgress() throws Exception {
    Goal goal = new Goal("goal", 10_00);

    Percent percent = goal.progressMadeWith(11_00);

    assertThat(percent.roundToInteger())
        .isEqualTo(100);
  }

  @Test
  public void amountRemainingIsTargetAmountMinusGivenAmount() throws Exception {
    Goal goal = new Goal("goal", 10_00);

    int remaining = goal.remainingAmount(9_00);

    assertThat(remaining)
        .isEqualTo(1_00);
  }

  @Test
  public void amountRemainingIsZeroIfGivenAmountExceedsTarget() throws Exception {
    Goal goal = new Goal("goal", 10_00);

    int remaining = goal.remainingAmount(22_00);

    assertThat(remaining)
        .isZero();
  }

}