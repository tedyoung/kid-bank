package com.learnwithted.kidbank.domain;

import java.util.List;

public interface GoalRepository {

  Goal save(Goal goal);

  List<Goal> findAll();

}
