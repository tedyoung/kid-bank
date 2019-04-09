package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.IntegrationTestConfiguration;
import com.learnwithted.kidbank.adapter.jpa.GoalJpaRepository;
import com.learnwithted.kidbank.adapter.jpa.GoalRepositoryJpaAdapter;
import com.learnwithted.kidbank.adapter.web.goal.GoalView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SuppressWarnings({"unchecked", "ConstantConditions"})
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "parent", roles = {"KID", "PARENT"})
@Import(IntegrationTestConfiguration.class)
public class GoalIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private GoalJpaRepository goalJpaRepository;

  @Autowired
  private GoalRepositoryJpaAdapter goalRepositoryJpaAdapter;

  @Before
  public void clearDatabase() {
    goalJpaRepository.deleteAll();
  }

  @Test
  public void noGoalsMessageAppearsWhenThereAreNoGoals() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/goals"))
                                 .andExpect(status().isOk())
                                 .andExpect(view().name("goals"))
                                 .andExpect(model().attributeExists("goals"))
                                 .andExpect(model().attributeExists("createGoal"))
                                 .andReturn();

    assertThat(mvcResult.getResponse().getContentAsString())
        .contains("There are no currently active Goals.")
        .contains("<form method=\"post\" action=\"/goals/create\">")
        .doesNotContain("<th>Goal</th>");
  }

  @Test
  public void createNewGoalDisplaysAllGoals() throws Exception {
    mockMvc.perform(post("/goals/create")
                        .param("targetAmount", "59.99")
                        .param("description", "Nintendo Splatoon 2"))
           .andExpect(redirectedUrl("/goals"));

    MvcResult mvcResult = mockMvc.perform(get("/goals"))
                                 .andReturn();

    List<GoalView> goalViews = (List<GoalView>) mvcResult.getModelAndView()
                                                         .getModel()
                                                         .get("goals");

    assertThat(goalViews)
        .containsExactly(new GoalView("Nintendo Splatoon 2", "$59.99"));
  }

}
