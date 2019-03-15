package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaUserProfileRepositoryTest {

  @Autowired
  JpaUserProfileRepository jpaUserProfileRepository;

  @Test
  public void findByPhoneNumberShouldReturnMatchingUserProfile() throws Exception {
    PhoneNumber phoneNumber = new PhoneNumber("+19876543210");
    UserProfile profile = new UserProfile("test", phoneNumber, "email", Role.PARENT);
    jpaUserProfileRepository.save(profile);

    assertThat(jpaUserProfileRepository.findByPhoneNumber(phoneNumber))
        .isPresent()
        .get().hasNoNullFieldsOrProperties();
  }

}