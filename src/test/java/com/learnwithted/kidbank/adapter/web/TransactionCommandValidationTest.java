package com.learnwithted.kidbank.adapter.web;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    TransactionCommand transactionCommand = TransactionCommand.createWithTodayDate();
    transactionCommand.setAmount(BigDecimal.ZERO);

    Set<ConstraintViolation<TransactionCommand>> constraintViolations =
        localValidatorFactory.validate(transactionCommand);

    assertThat(constraintViolations)
        .hasSize(1);

    Object invalidValue = constraintViolations
                              .iterator()
                              .next()
                              .getInvalidValue();

    assertThat(invalidValue)
        .isInstanceOf(BigDecimal.class);

  }

  @Test
  public void amountLessThanZeroShouldBeNotValid() throws Exception {

    TransactionCommand transactionCommand = TransactionCommand.createWithTodayDate();
    transactionCommand.setAmount(BigDecimal.valueOf(-1));

    Set<ConstraintViolation<TransactionCommand>> constraintViolations =
        localValidatorFactory.validate(transactionCommand);

    assertThat(constraintViolations)
        .hasSize(1);

    Object invalidValue = constraintViolations
                              .iterator()
                              .next()
                              .getInvalidValue();

    assertThat(invalidValue)
        .isInstanceOf(BigDecimal.class);

  }

  @Test
  public void amountGreaterThanZeroShouldBeValid() throws Exception {
    TransactionCommand transactionCommand = TransactionCommand.createWithTodayDate();
    transactionCommand.setAmount(BigDecimal.ONE);

    Set<ConstraintViolation<TransactionCommand>> constraintViolations =
        localValidatorFactory.validate(transactionCommand);

    assertThat(constraintViolations)
        .isEmpty();
  }

  @Test
  public void dateInFutureShouldNotBeValid() throws Exception {
    TransactionCommand transactionCommand = new TransactionCommand();
    transactionCommand.setAmount(BigDecimal.ONE); // valid
    transactionCommand.setDate("2100-02-12"); // invalid future date

    Set<ConstraintViolation<TransactionCommand>> constraintViolations =
        localValidatorFactory.validate(transactionCommand);

    assertThat(constraintViolations)
        .hasSize(1);

    Object invalidValue = constraintViolations
                              .iterator()
                              .next()
                              .getInvalidValue();

    assertThat(invalidValue)
        .isInstanceOf(LocalDateTime.class);
  }

}
