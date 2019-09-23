package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.DummyUserProfile;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SpendControllerTest {

  @Mock(stubOnly = true)
  private BindingResult mockBindingResult;

  @Test
  public void spendCommandShouldReduceAmountInAccount() throws Exception {
    TransactionCommand spendCommand = TransactionCommand.createWithTodayDate();
    spendCommand.setAmount(BigDecimal.valueOf(34.79));

    Account account = TestAccountBuilder.builder().buildAsCore();

    SpendController spendController = new SpendController(account);

    Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
    spendController.processSpendCommand(spendCommand, mockBindingResult, new DummyUserProfile());

    assertThat(account.balance())
        .isEqualTo(-3479);
  }

}
