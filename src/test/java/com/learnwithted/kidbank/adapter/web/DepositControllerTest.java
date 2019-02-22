package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.StubBalanceChangeNotifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DepositControllerTest {

  @Mock(stubOnly = true)
  private BindingResult mockBindingResult;

  @Test
  public void depositCommandShouldAddAmountToAccount() throws Exception {
    TransactionCommand depositCommand = TransactionCommand.createWithTodayDate();
    depositCommand.setAmount(BigDecimal.valueOf(12.34));

    Account account = new Account(new FakeTransactionRepository(), new StubBalanceChangeNotifier());

    DepositController depositController = new DepositController(account);

    Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
    depositController.processDepositCommand(depositCommand, mockBindingResult);

    assertThat(account.balance())
        .isEqualTo(1234);
  }

}
