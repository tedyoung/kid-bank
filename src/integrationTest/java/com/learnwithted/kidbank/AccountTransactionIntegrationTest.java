package com.learnwithted.kidbank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountTransactionIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void homePageExists() throws Exception {
    mockMvc.perform(get("/"))
           .andExpect(status().isOk());
  }

  @Test
  public void submitDepositAddsAmountToAccount() throws Exception {
    mockMvc.perform(post("/deposit")
                        .param("amount", "12.45")
                        .param("date", "2000-01-02")
                        .param("description", "the source of money"))
           .andExpect(redirectedUrl("/"));

    mockMvc.perform(get("/"))
           .andExpect(model().attribute("balance", "$12.45"));
  }

  @Test
  public void submitSpendShouldReduceAmountInAccount() throws Exception {
    mockMvc.perform(post("/spend")
                        .param("amount", "87.34")
                        .param("date", "2001-11-12")
                        .param("description", "you spent it on what?"))
           .andExpect(redirectedUrl("/"));

    mockMvc.perform(get("/"))
           .andExpect(model().attribute("balance", "-$87.34"));
  }

}
