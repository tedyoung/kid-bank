package com.learnwithted.kidbank.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class UserProfile {

  // EXTRINSIC property used by Repository
  @Getter
  @Setter
  private Long id;

  private final String name;
  private final PhoneNumber phoneNumber; // SMS authorization
  private final String email; // web-based/OAuth authorization
  private final Role role;

  public UserProfile(String name, PhoneNumber phoneNumber, String email, Role role) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.role = role;
  }

  public Role role() {
    return role;
  }

  public String name() {
    return name;
  }

  public String email() {
    return email;
  }

  public PhoneNumber phoneNumber() {
    return phoneNumber;
  }
}
