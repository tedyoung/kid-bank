package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.IntegrationTestConfiguration;
import com.learnwithted.kidbank.adapter.jpa.UserProfileJpaRepository;
import com.learnwithted.kidbank.adapter.jpa.UserProfileRepositoryJpaAdapter;
import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings({"unchecked", "ConstantConditions"})
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "parent", roles = {"KID", "PARENT"})
@Import(IntegrationTestConfiguration.class)
public class UserManagementIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserProfileJpaRepository userProfileJpaRepository;

  @Autowired
  private UserProfileRepositoryJpaAdapter userProfileRepositoryJpaAdapter;

  @Before
  public void clearUserProfileDatabase() {
    userProfileJpaRepository.deleteAll();
  }

  @Test
  public void getAgainstUsersDisplaysListOfUserProfiles() throws Exception {
    UserProfile ted = new UserProfile("Ted", new PhoneNumber("+16505551212"), "ted@tedmyoung.com", Role.PARENT);
    userProfileRepositoryJpaAdapter.save(ted);

    MvcResult mvcResult = mockMvc.perform(get("/users"))
                                 .andExpect(status().isOk())
                                 .andExpect(view().name("users"))
                                 .andExpect(model().attributeExists("userProfiles"))
                                 .andReturn();

    List<UserProfileView> userProfiles = (List<UserProfileView>) mvcResult.getModelAndView()
                                                                          .getModel()
                                                                          .get("userProfiles");
    assertThat(userProfiles)
        .hasSize(1);

    String body = mvcResult.getResponse().getContentAsString();
    assertThat(body)
        .contains("Ted", "+16505551212", "Parent", "ted@tedmyoung.com", "Send Welcome");
  }

}
