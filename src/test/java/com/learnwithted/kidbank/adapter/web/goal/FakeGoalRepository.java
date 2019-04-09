package com.learnwithted.kidbank.adapter.web.goal;

import com.learnwithted.kidbank.domain.Goal;
import com.learnwithted.kidbank.domain.GoalRepository;

import java.util.Collections;
import java.util.List;

public class FakeGoalRepository implements GoalRepository {
  private Goal goal;

  @Override
  public Goal save(Goal goal) {
    this.goal = goal;
    this.goal.setId(33L);
    return this.goal;
  }

  @Override
  public List<Goal> findAll() {
    return Collections.singletonList(goal);
  }
}
