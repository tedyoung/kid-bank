package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.Action;
import com.learnwithted.kidbank.domain.Transaction;
import com.learnwithted.kidbank.domain.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transactions")
class TransactionDto {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private LocalDateTime date;
  private String action;
  private int amount;
  private String source;
  @ManyToOne
  private UserProfileDto creator;

  static TransactionDto from(Transaction transaction) {
    UserProfileDto userProfileDto = transaction.creator()
                                               .map(UserProfileDto::from)
                                               .orElse(null);

    return new TransactionDto(transaction.getId(),
                              transaction.dateTime(),
                              transaction.action().toString(),
                              transaction.amount(),
                              transaction.source(),
                              userProfileDto);
  }

  Transaction asTransaction() {
    Action actionEnum = Action.valueOf(action.toUpperCase());

    // In the database, we currently allow the "creator" column to be null
    // for both backwards-compatibility and for transactions loaded through "import CSV"
    UserProfile userProfile = creator == null ? null : creator.asUserProfile();

    return new Transaction(id, date, actionEnum, amount, source, userProfile);
  }
}
