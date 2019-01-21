package com.learnwithted.kidbank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountTransactionViewIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void depositToNewAccountShouldHaveOneDepositTransaction() throws Exception {
    mockMvc.perform(post("/deposit")
                        .param("amount", "12.45")
                        .param("source", "Birthday gift")
                        .param("date", "2/9/2018"))
           .andExpect(redirectedUrl("/"));

    MvcResult mvcResult = mockMvc.perform(get("/"))
                                 .andReturn();
    Collection<TransactionView> transactions = transactionsFromModel(mvcResult);

    assertThat(transactions)
        .containsExactly(new TransactionView("02/09/2018", "Deposit", "$12.45", "Birthday gift"));
  }

  @Test
  public void newAccountViewHasNoTransactions() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/"))
                                 .andReturn();
    Collection transactions = transactionsFromModel(mvcResult);
    assertThat(transactions)
        .isEmpty();
  }

  @SuppressWarnings({"unchecked", "ConstantConditions"})
  private Collection<TransactionView> transactionsFromModel(MvcResult mvcResult) {
    return (Collection<TransactionView>) mvcResult
                                             .getModelAndView()
                                             .getModel()
                                             .get("transactions");
  }
}
