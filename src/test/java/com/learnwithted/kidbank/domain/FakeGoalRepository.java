package com.learnwithted.kidbank.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeGoalRepository implements GoalRepository {
  private List<Goal> goals;
  private AtomicLong counter = new AtomicLong();

  public FakeGoalRepository() {
    goals = new ArrayList<>();
  }

  public FakeGoalRepository(Goal... goals) {
    this.goals = Arrays.asList(goals);
  }

  @Override
  public Goal save(Goal goal) {
    goal.setId(counter.getAndIncrement());
    goals.add(goal);
    return goal;
  }

  @Override
  public List<Goal> findAll() {
    return goals;
  }
}
