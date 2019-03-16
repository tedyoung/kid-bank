package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
class UserProfileDto {
  private String name;
  private String phoneNumber;
  private String email;
  private Role role;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public static UserProfileDto from(UserProfile userProfile) {
    UserProfileDto dto = new UserProfileDto();

    dto.setId(userProfile.getId());
    dto.setName(userProfile.name());
    dto.setEmail(userProfile.email());
    dto.setPhoneNumber(userProfile.phoneNumber().asRaw());
    dto.setRole(userProfile.role());

    return dto;
  }

  UserProfile asUserProfile() {
    UserProfile userProfile = new UserProfile(name, new PhoneNumber(phoneNumber), email, role);
    userProfile.setId(id);
    return userProfile;
  }
}
