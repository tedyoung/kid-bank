package com.learnwithted.kidbank.domain;

public class DummyUserProfile extends UserProfile {
  public DummyUserProfile() {
    super("Dummy", new PhoneNumber("+16505551212"), "dummy@example.com", Role.UNKNOWN);
  }
}
