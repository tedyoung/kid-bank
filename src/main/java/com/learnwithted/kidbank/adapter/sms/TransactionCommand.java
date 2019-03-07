package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Role;

public interface TransactionCommand {
  String execute(Role role);
}
