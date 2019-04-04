package com.learnwithted.kidbank.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StubGoalRepository implements GoalRepository {
  private final List<Goal> goals;

  public StubGoalRepository() {
    goals = Collections.emptyList();
  }

  public StubGoalRepository(Goal... goals) {
    this.goals = Arrays.asList(goals);
  }

  @Override
  public Goal save(Goal goal) {
    return goal;
  }

  @Override
  public List<Goal> findAll() {
    return goals;
  }
}
