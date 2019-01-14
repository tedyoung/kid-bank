package com.learnwithted.kidbank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountTransactionViewIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void newAccountViewHasNoTransactions() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/"))
                                 .andReturn();
    Collection transactions = (Collection) mvcResult.getModelAndView().getModel().get("transactions");
    assertThat(transactions)
        .isEmpty();
  }

}
