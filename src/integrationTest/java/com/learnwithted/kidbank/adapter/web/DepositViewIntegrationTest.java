package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.IntegrationTestConfiguration;
import com.learnwithted.kidbank.domain.DummyUserProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "parent", roles = {"KID", "PARENT"})
@Import(IntegrationTestConfiguration.class)
public class DepositViewIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getAgainstDepositUriShouldReturnDepositFormPage() throws Exception {
    mockMvc.perform(get("/deposit"))
           .andExpect(status().isOk())
           .andExpect(view().name("deposit"))
           .andExpect(model().attributeExists("balance", "depositCommand"));
  }

  @Test
  public void submitDepositFormRedirectsToHomePage() throws Exception {
    mockMvc.perform(post("/deposit")
                        .with(authentication(
                            new TestingAuthenticationToken(new DummyUserProfile(), null, "ROLE_PARENT")))
                        .param("amount", "12.45")
                        .param("date", "2000-01-02")
                        .param("description", "the source of money"))
           .andExpect(redirectedUrl(AccountController.ACCOUNT_URL));

    mockMvc.perform(get(AccountController.ACCOUNT_URL))
           .andExpect(status().isOk());
  }

  @Test
  public void submitNegativeAmountShouldDisplayErrorMessage() throws Exception {
    mockMvc.perform(post("/deposit")
                        .param("amount", "-11.23")
                        .param("date", "2018-09-25")
                        .param("description", "ha ha, this shouldn't be allowed"))
           .andExpect(model().hasErrors())
           .andExpect(model().attributeHasErrors("depositCommand"))
           .andExpect(model().attributeHasFieldErrors("depositCommand", "amount"));
  }
}
