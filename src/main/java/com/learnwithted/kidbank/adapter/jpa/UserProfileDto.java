package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_profiles")
class UserProfileDto {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private String phone;
  private String email;
  private String role;

  public static UserProfileDto from(UserProfile userProfile) {
    return new UserProfileDto(userProfile.getId(),
                              userProfile.name(),
                              userProfile.phoneNumber().asRaw(),
                              userProfile.email(),
                              userProfile.role().name());
  }

  UserProfile asUserProfile() {
    return new UserProfile(id,
                           name,
                           new PhoneNumber(phone),
                           email,
                           Role.valueOf(role));
  }
}
