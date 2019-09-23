package com.learnwithted.kidbank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserProfile that = (UserProfile) o;

    return id != null ? id.equals(that.id) : that.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
