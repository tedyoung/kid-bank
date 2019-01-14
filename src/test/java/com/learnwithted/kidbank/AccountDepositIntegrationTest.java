package com.learnwithted.kidbank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountDepositIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void homePageExists() throws Exception {
    mockMvc.perform(get("/"))
           .andExpect(status().isOk());
  }

  @Test
  public void submitDepositAddsAmountToAccount() throws Exception {
    mockMvc.perform(post("/deposit").param("amount", "12.45"))
           .andExpect(redirectedUrl("/"));

    // verify model has correct balance
    mockMvc.perform(get("/"))
           .andExpect(model().attribute("balance", "$12.45"));
  }

}
