package com.learnwithted.kidbank.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
public class UserProfile {

  private String name;
  private PhoneNumber phoneNumber; // SMS authorization
  private String email; // web-based/OAuth authorization
  private Role role;

  // EXTRINSIC property used by Repository
  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
}
