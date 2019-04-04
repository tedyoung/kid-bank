package com.learnwithted.kidbank.adapter.jpa;

import com.learnwithted.kidbank.domain.PhoneNumber;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.UserProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class UserProfileJpaAdapterTest {

  @Autowired
  UserProfileJpaRepository repository;

  @Before
  public void clear() {
    repository.deleteAll();
  }

  @Test
  public void newUserProfileShouldHaveIdAfterSaving() throws Exception {
    UserProfileRepositoryJpaAdapter jpaAdapter = new UserProfileRepositoryJpaAdapter(repository);

    UserProfile userProfile = new UserProfile("test", new PhoneNumber("+16505550000"), "email", Role.UNKNOWN);

    UserProfile saved = jpaAdapter.save(userProfile);

    assertThat(saved.getId())
        .isNotNull();
  }

  @Test
  public void savedUserIsReturnedViaFindById() throws Exception {
    UserProfileRepositoryJpaAdapter jpaAdapter = new UserProfileRepositoryJpaAdapter(repository);
    UserProfile userProfile = new UserProfile("findByIdUser", new PhoneNumber("+14155551212"), "email", Role.UNKNOWN);
    Long profileId = jpaAdapter.save(userProfile).getId();

    assertThat(jpaAdapter.findById(profileId))
        .isPresent()
        .get().extracting(UserProfile::name)
        .isEqualTo("findByIdUser");
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

  @Test
  public void findAllReturnsAllProfiles() throws Exception {
    UserProfileRepositoryJpaAdapter jpaAdapter = new UserProfileRepositoryJpaAdapter(repository);
    jpaAdapter.save(new UserProfile("The Kid", new PhoneNumber("+16501234567"), "kid@example.com", Role.KID));
    jpaAdapter.save(new UserProfile("The Parent", new PhoneNumber("+16505554567"), "parent@example.com", Role.PARENT));

    List<UserProfile> all = jpaAdapter.findAll();

    assertThat(all)
        .hasSize(2)
        .extracting(UserProfile::name)
        .containsExactlyInAnyOrder("The Kid", "The Parent");
  }
}
