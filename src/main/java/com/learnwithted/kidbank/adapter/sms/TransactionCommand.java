package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.UserProfile;

public interface TransactionCommand {
  String execute(UserProfile userProfile);
}
