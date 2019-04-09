package com.learnwithted.kidbank.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class AccountGoalsTest {

  @Test
  public void newAccountHasNoGoals() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    assertThat(account.goals())
        .isEmpty();
  }

  @Test
  public void addGoalDirectlyToRepositoryIsFoundViaAccount() throws Exception {
    TestAccountBuilder builder = TestAccountBuilder.builder();
    GoalRepository goalRepository = builder.goalRepository();

    Account account = builder.buildAsCore();

    Goal coolThing = new Goal("Cool Thing", 87_65);
    goalRepository.save(coolThing);

    assertThat(account.goals())
        .isNotEmpty()
        .usingElementComparatorIgnoringFields("id")
        .containsExactly(coolThing);
  }

  @Test
  public void createdGoalIsReturnedInSetOfAllGoals() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    account.createGoal("description", 65_00);

    Goal expectedGoal = new Goal("description", 65_00);

    assertThat(account.goals())
        .usingElementComparatorIgnoringFields("id")
        .containsOnly(expectedGoal);
  }

  @Test
  public void creatingGoalSavesGoalInRepository() throws Exception {
    GoalRepository repositorySpy = spy(FakeGoalRepository.class);
    CoreAccount account = TestAccountBuilder.builder()
                                            .withGoalRepository(repositorySpy)
                                            .buildAsCore();

    account.createGoal("description", 65_00);

    verify(repositorySpy).save(any(Goal.class));
  }

  @Test
  public void accountLoadsGoalsFromRepositoryUponCreation() throws Exception {
    Goal first = new Goal("first", 10_00, 1L);
    Goal second = new Goal("second", 20_00, 2L);
    Goal third = new Goal("third", 30_00, 3L);

    GoalRepository goalRepository = new FakeGoalRepository(first, second, third);
    Account account = TestAccountBuilder.builder()
                                        .withGoalRepository(goalRepository)
                                        .buildAsCore();

    assertThat(account.goals())
        .containsExactlyInAnyOrder(first, second, third);
  }
}