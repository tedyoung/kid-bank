package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.IntegrationTestConfiguration;
import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.UserProfile;
import com.learnwithted.kidbank.domain.UserProfileRepository;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "parent", roles = {"KID", "PARENT"})
@Import(IntegrationTestConfiguration.class)
public class UserProfileCreationIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserProfileRepository userProfileRepository;

  @Test
  public void userProfilePostedCorrectly() throws Exception {
    mockMvc.perform(post("/users/create")
                        .param("name", "Hot Diggity")
                        .param("email", "hdiggity@foo.bar")
                        .param("phoneNumber", "+11234567890")
                        .param("role", "parent"))
           .andExpect(redirectedUrl("/users"));

    Optional<UserProfile> profile = userProfileRepository
                                        .findByPhoneNumber(new PhoneNumber("+11234567890"));
    assertThat(profile)
        .isPresent();
  }

  @Test
  public void getUsersCreateReturnsPageWithCreateUserForm() throws Exception {
    mockMvc.perform(get("/users/create"))
           .andExpect(status().isOk())
           .andExpect(view().name("create-user"))
           .andExpect(model().attributeExists("createUserProfile"));

  }

}
