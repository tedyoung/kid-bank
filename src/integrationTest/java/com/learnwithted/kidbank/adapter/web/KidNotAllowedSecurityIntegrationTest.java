package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.IntegrationTestConfiguration;
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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "kid", roles = {"KID"})
@Import(IntegrationTestConfiguration.class)
public class KidNotAllowedSecurityIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void kidShouldNotBeAllowedToDeposit() throws Exception {
    mockMvc.perform(get("/deposit"))
           .andExpect(status().isForbidden());
  }

  @Test
  public void kidShouldNotBeAllowedToSpend() throws Exception {
    mockMvc.perform(get("/spend"))
           .andExpect(status().isForbidden());
  }

  @Test
  public void kidShouldNotBeAllowedToImportCsv() throws Exception {
    mockMvc.perform(get("/import"))
           .andExpect(status().isForbidden());
  }

  @Test
  public void kidShouldNotSeeLinkToDeposit() throws Exception {
    mockMvc.perform(get("/"))
           .andExpect(content().string(not(containsString("Deposit Money"))));
  }

}
