package com.learnwithted.kidbank.adapter.web.goal;

import com.learnwithted.kidbank.domain.FakeGoalRepository;
import com.learnwithted.kidbank.domain.Goal;
import com.learnwithted.kidbank.domain.GoalRepository;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GoalServiceTest {

  @Test
  public void createGoalSavesNewGoalInRepository() throws Exception {
    FakeGoalRepository fakeGoalRepository = new FakeGoalRepository();
    GoalService goalService = new GoalService(fakeGoalRepository);

    CreateGoal createGoal = new CreateGoal("Lofty Goal", BigDecimal.valueOf(98.35));

    goalService.create(createGoal);

    assertThat(fakeGoalRepository.findAll())
        .usingElementComparatorIgnoringFields("id")
        .containsExactly(new Goal("Lofty Goal", 98_35));
  }

  @Test
  public void findAllReturnsNoGoalViewsWhenRepositoryIsEmpty() throws Exception {
    GoalService goalService = new GoalService(new GoalRepository() {
      @Override
      public Goal save(Goal goal) {
        return null;
      }

      @Override
      public List<Goal> findAll() {
        return Collections.emptyList();
      }
    });

    List<GoalView> goalViews = goalService.findAll();

    assertThat(goalViews)
        .isEmpty();
  }

  @Test
  public void findAllReturnsOneGoalWhenOneGoalInRepository() throws Exception {
    GoalRepository goalRepository = new GoalRepository() {
      @Override
      public Goal save(Goal goal) {
        return null;
      }

      @Override
      public List<Goal> findAll() {
        return Collections.singletonList(new Goal("Nintendo Splatoon 2", 60_00));
      }
    };

    GoalService goalService = new GoalService(goalRepository);

    List<GoalView> goalViews = goalService.findAll();

    assertThat(goalViews)
        .containsExactly(new GoalView("Nintendo Splatoon 2", "$60.00"));
  }

}