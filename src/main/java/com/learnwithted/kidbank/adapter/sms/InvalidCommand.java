package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Role;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class InvalidCommand implements TransactionCommand {
  private final String originalText;

  @Override
  public String execute(Role role) {
    return "Did not understand \"" + originalText + "\".";
  }
}
