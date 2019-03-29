package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserProfileTest {

  @Test
  public void convertsToUserProfileDomain() throws Exception {
    CreateUserProfile createUserProfile = new CreateUserProfile("Hot Diggity",
                                                                "+11234567890",
                                                                "hdiggity@foo.bar",
                                                                "parent");

    UserProfile expectUserProfile = new UserProfile("Hot Diggity",
                                                    new PhoneNumber("+11234567890"),
                                                    "hdiggity@foo.bar",
                                                    Role.PARENT);

    assertThat(createUserProfile.asUserProfile())
        .isEqualToIgnoringGivenFields(expectUserProfile, "id");
  }

}