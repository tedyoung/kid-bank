package com.learnwithted.kidbank.adapter.web.goal;

import com.learnwithted.kidbank.domain.FakeGoalRepository;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class GoalControllerTest {

  @Test
  public void createNewGoalCreatesGoalInRepository() throws Exception {
    GoalService spy = spy(new GoalService(new FakeGoalRepository()));

    GoalController goalController = new GoalController(spy);

    CreateGoal createGoal = new CreateGoal("Hearts of Iron IV", new BigDecimal(39.99));
    String redirectPage = goalController.createNewGoal(createGoal);

    verify(spy).create(createGoal);

    assertThat(redirectPage)
        .isEqualTo("redirect:/goals");
  }

}