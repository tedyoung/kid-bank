package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Goal;
import com.learnwithted.kidbank.domain.GoalRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class GoalJpaAdapterTest {

  @Autowired
  private GoalJpaRepository repository;

  @Before
  public void clear() {
    repository.deleteAll();
  }

  @Test
  public void newGoalIsAssignedId() throws Exception {
    GoalRepository adapter = new GoalRepositoryJpaAdapter(repository);

    Goal goal = new Goal("goal", 10_00);

    Goal savedGoal = adapter.save(goal);

    assertThat(savedGoal.getId())
        .isNotNull();
    assertThat(savedGoal.description())
        .isEqualTo("goal");
    assertThat(savedGoal.targetAmount())
        .isEqualTo(10_00);
  }

  @Test
  public void findAllReturnsAllGoals() throws Exception {
    GoalRepository adapter = new GoalRepositoryJpaAdapter(repository);
    Goal first = new Goal("first", 55_00);
    adapter.save(first);
    Goal second = new Goal("second", 25_00);
    adapter.save(second);

    List<Goal> goals = adapter.findAll();

    assertThat(goals)
        .usingElementComparatorIgnoringFields("id")
        .containsExactlyInAnyOrder(first, second);

  }

}
