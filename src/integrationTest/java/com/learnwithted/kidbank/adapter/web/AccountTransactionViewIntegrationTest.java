package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.IntegrationTestConfiguration;
import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
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
    UserProfile parentProfile = new UserProfile("The Parent",
                                                new PhoneNumber("+15555555555"),
                                                "parent@example.com",
                                                Role.PARENT);

    mockMvc.perform(post("/deposit")
                        .with(authentication(
                            new TestingAuthenticationToken(parentProfile, null, "ROLE_PARENT")))
                        .param("amount", "12.45")
                        .param("description", "Birthday gift")
                        .param("date", "2018-02-09"))
           .andExpect(redirectedUrl(AccountController.ACCOUNT_URL));

    Collection<TransactionView> transactions = transactionsFromModel();

    assertThat(transactions)
        .contains(new TransactionView(
            "02/09/2018", "Deposit", "$12.45", "$12.45", "Birthday gift", "The Parent"));
  }

  @Test
  public void spendToNewAccountShouldHaveOneSpendTransaction() throws Exception {
    UserProfile parentProfile = new UserProfile("Parent Spender",
                                                new PhoneNumber("+15555555555"),
                                                "parent@example.com",
                                                Role.PARENT);

    mockMvc.perform(post("/spend")
                        .with(authentication(
                            new TestingAuthenticationToken(parentProfile, null, "ROLE_PARENT")))
                        .param("date", "2018-12-19")
                        .param("amount", "49.95")
                        .param("description", "Video game"))
           .andExpect(redirectedUrl(AccountController.ACCOUNT_URL));

    Collection<TransactionView> transactions = transactionsFromModel();

    assertThat(transactions)
        .contains(new TransactionView(
            "12/19/2018", "Spend", "$49.95", "-$49.95", "Video game", "Parent Spender"));
  }

  @Test
  public void newAccountViewHasNoTransactions() throws Exception {
    Collection<TransactionView> transactions = transactionsFromModel();
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
