package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class UserProfileJpaTest {

  @Autowired
  UserProfileJpaRepository repository;

  @Test
  public void newUserProfileShouldHaveIdAfterSaving() throws Exception {
    UserProfileRepositoryJpaAdapter jpaAdapter = new UserProfileRepositoryJpaAdapter(repository);

    UserProfile userProfile = new UserProfile("test", new PhoneNumber("phone"), "email", Role.UNKNOWN);

    UserProfile saved = jpaAdapter.save(userProfile);

    assertThat(saved.getId())
        .isNotNull();
  }

  @Test
  public void savedProfileShouldBeFoundByPhoneNumber() throws Exception {
    UserProfileRepositoryJpaAdapter jpaAdapter = new UserProfileRepositoryJpaAdapter(repository);

    PhoneNumber phoneNumber = new PhoneNumber("+16501234567");
    UserProfile userProfile = new UserProfile("test", phoneNumber, "email", Role.UNKNOWN);

    jpaAdapter.save(userProfile);

    Optional<UserProfile> found = jpaAdapter.findByPhoneNumber(phoneNumber);
    assertThat(found)
        .isPresent()
        .get().isEqualToIgnoringGivenFields(userProfile, "id");
  }
}
