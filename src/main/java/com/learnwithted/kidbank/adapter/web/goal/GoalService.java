package com.learnwithted.kidbank.adapter.web.goal;

import com.learnwithted.kidbank.domain.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalService {
  private final GoalRepository goalRepository;

  @Autowired
  public GoalService(GoalRepository goalRepository) {
    this.goalRepository = goalRepository;
  }

  public List<GoalView> findAll() {
    return goalRepository.findAll().stream()
                         .map(GoalView::from)
                         .collect(Collectors.toList());
  }

  public void create(CreateGoal createGoal) {
    goalRepository.save(createGoal.asGoal());
  }
}
