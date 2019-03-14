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
@WithMockUser(username = "parent", roles = {"KID", "PARENT"})
@Import(IntegrationTestConfiguration.class)
public class AccountTransactionViewIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void depositToNewAccountShouldHaveOneDepositTransaction() throws Exception {
    mockMvc.perform(post("/deposit")
                        .param("amount", "12.45")
                        .param("description", "Birthday gift")
                        .param("date", "2018-02-09"))
           .andExpect(redirectedUrl(AccountController.ACCOUNT_URL));

    Collection<TransactionView> transactions = transactionsFromModel();

    assertThat(transactions)
        .contains(new TransactionView("02/09/2018", "Deposit", "$12.45", "Birthday gift"));
  }

  @Test
  public void newAccountViewHasNoTransactions() throws Exception {

    Collection transactions = transactionsFromModel();
    assertThat(transactions)
        .isEmpty();
  }

  @SuppressWarnings({"unchecked", "ConstantConditions"})
  private Collection<TransactionView> transactionsFromModel() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get(AccountController.ACCOUNT_URL))
                                 .andReturn();
    assertThat(mvcResult.getResponse().getStatus())
        .isEqualTo(200);
    return (Collection<TransactionView>) mvcResult
                                             .getModelAndView()
                                             .getModel()
                                             .get("transactions");
  }
}
