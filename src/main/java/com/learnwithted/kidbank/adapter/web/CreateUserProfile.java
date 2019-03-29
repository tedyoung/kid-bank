package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserProfile {
  private String name;
  private String phoneNumber;
  private String email;
  private String role;

  UserProfile asUserProfile() {
    return new UserProfile(name,
                           new PhoneNumber(phoneNumber),
                           email,
                           Role.valueOf(role.toUpperCase()));
  }
}
