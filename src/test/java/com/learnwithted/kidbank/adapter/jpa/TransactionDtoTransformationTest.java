package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.*;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionDtoTransformationTest {
  
  @Test
  public void transactionConvertedToDtoKeepsUserProfile() throws Exception {
    UserProfile creator = new UserProfile("testuser", new PhoneNumber("+16505551212"), "email", Role.UNKNOWN);
    Transaction transaction = new Transaction(1L, LocalDateTime.now(), Action.DEPOSIT, 4_55, "test", creator);

    TransactionDto dto = TransactionDto.from(transaction);

    assertThat(dto.getCreator().asUserProfile())
        .isEqualToComparingFieldByFieldRecursively(creator);
  }

  @Test
  public void dtoToTransactionKeepsUserProfile() throws Exception {
    UserProfileDto creatorDto = new UserProfileDto(2L, "testuser", "+16505551212", "email", "UNKNOWN");
    TransactionDto dto = new TransactionDto(3L, LocalDateTime.now(), "SPEND", 9_87, "test", creatorDto);

    Transaction transaction = dto.asTransaction();

    UserProfile expectedProfile = creatorDto.asUserProfile();
    assertThat(transaction.creator().get())
        .isEqualToComparingFieldByFieldRecursively(expectedProfile);
  }

}