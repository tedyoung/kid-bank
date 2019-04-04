package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Goal;
import com.learnwithted.kidbank.domain.GoalRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GoalRepositoryJpaAdapter implements GoalRepository {

  private final GoalJpaRepository goalJpaRepository;

  public GoalRepositoryJpaAdapter(GoalJpaRepository goalJpaRepository) {
    this.goalJpaRepository = goalJpaRepository;
  }

  @Override
  public Goal save(Goal goal) {
    GoalDto dto = GoalDto.from(goal);

    GoalDto savedDto = goalJpaRepository.save(dto);

    return savedDto.asGoal();
  }

  @Override
  public List<Goal> findAll() {
    return goalJpaRepository.findAll()
                            .stream()
                            .map(GoalDto::asGoal)
                            .collect(Collectors.toList());
  }
}
