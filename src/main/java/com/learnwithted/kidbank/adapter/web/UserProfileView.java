package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.UserProfile;
import lombok.Data;

@Data
public class UserProfileView {

  private final String name;
  private final String email;
  private final String phoneNumber;
  private final String role;

  public static UserProfileView of(UserProfile userProfile) {
    String role = userProfile.role().name();
    role = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase();
    return new UserProfileView(userProfile.name(),
                               "redacted@example.com",
                               "+16505551212",
                               role);
  }
}
