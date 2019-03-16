package com.learnwithted.kidbank.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class UserProfile {

  private String name;
  private PhoneNumber phoneNumber; // SMS authorization
  private String email; // web-based/OAuth authorization
  private Role role;

  // EXTRINSIC property used by Repository
  @Getter
  @Setter
  private Long id;

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
