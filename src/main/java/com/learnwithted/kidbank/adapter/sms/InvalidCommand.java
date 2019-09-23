package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.UserProfile;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class InvalidCommand implements TransactionCommand {
  private final String originalText;

  @Override
  public String execute(UserProfile userProfile) {
    return "Did not understand \"" + originalText + "\".";
  }
}
