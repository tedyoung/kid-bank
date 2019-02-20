package com.learnwithted.kidbank.adapter.web;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionCommandValidationTest {

  private LocalValidatorFactoryBean localValidatorFactory;

  @Before
  public void setup() {
    localValidatorFactory = new LocalValidatorFactoryBean();
    localValidatorFactory.setProviderClass(HibernateValidator.class);
    localValidatorFactory.afterPropertiesSet();
  }

  @Test
  public void amountAsZeroShouldBeNotValid() throws Exception {

    TransactionCommand transactionCommand = new TransactionCommand();
    transactionCommand.setAmount(BigDecimal.ZERO);

    Set<ConstraintViolation<TransactionCommand>> constraintViolations =
        localValidatorFactory.validate(transactionCommand);

    assertThat(constraintViolations)
        .hasSize(1);
  }

  @Test
  public void amountLessThanZeroShouldBeNotValid() throws Exception {

    TransactionCommand transactionCommand = new TransactionCommand();
    transactionCommand.setAmount(BigDecimal.valueOf(-1));

    Set<ConstraintViolation<TransactionCommand>> constraintViolations =
        localValidatorFactory.validate(transactionCommand);

    assertThat(constraintViolations)
        .hasSize(1);
  }

  @Test
  public void amountGreaterThanZeroShouldBeValid() throws Exception {
    TransactionCommand transactionCommand = new TransactionCommand();
    transactionCommand.setAmount(BigDecimal.ONE);

    Set<ConstraintViolation<TransactionCommand>> constraintViolations =
        localValidatorFactory.validate(transactionCommand);

    assertThat(constraintViolations)
        .isEmpty();
  }

}
