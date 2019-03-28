package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "user_profiles")
class UserProfileDto {
  private String name;
  private String phone;
  private String email;
  private String role;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public static UserProfileDto from(UserProfile userProfile) {
    UserProfileDto dto = new UserProfileDto();

    dto.setId(userProfile.getId());
    dto.setName(userProfile.name());
    dto.setEmail(userProfile.email());
    dto.setPhone(userProfile.phoneNumber().asRaw());
    dto.setRole(userProfile.role().name());

    return dto;
  }

  UserProfile asUserProfile() {
    UserProfile userProfile = new UserProfile(name, new PhoneNumber(phone), email, Role.valueOf(role));
    userProfile.setId(id);
    return userProfile;
  }
}
