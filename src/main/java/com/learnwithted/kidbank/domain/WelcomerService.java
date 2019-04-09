package com.learnwithted.kidbank.domain;

import com.learnwithted.kidbank.app.TextMessageSender;
import com.learnwithted.kidbank.app.Welcomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class WelcomerService implements Welcomer {
  private final TextMessageSender textMessageSender;
  private final UserProfileRepository userProfileRepository;

  public WelcomerService(TextMessageSender textMessageSender, UserProfileRepository userProfileRepository) {
    this.textMessageSender = textMessageSender;
    this.userProfileRepository = userProfileRepository;
  }

  @Override
  public void welcome(Long profileId) {
    Optional<UserProfile> userProfile = userProfileRepository.findById(profileId);
    if (!userProfile.isPresent()) {
      log.warn("Could not find UserProfile with ID {}", profileId);
      return;
    }

    UserProfile profile = userProfile.get();
    String welcome = welcomeMessageFor(profile);
    textMessageSender.send(profile.phoneNumber().asRaw(), welcome);
  }

  String welcomeMessageFor(UserProfile profile) {
    String welcome = "Hi " + profile.name() + ", welcome to Kid Money Manager.";
    String commandIntro = "You can use the commands: ";

    String allowedCommands;
    if (profile.role() == Role.PARENT) {
      allowedCommands = "BALANCE, DEPOSIT, SPEND, and GOALS.";
    } else {
      allowedCommands = "BALANCE and GOALS.";
    }

    return welcome + " " + commandIntro + allowedCommands;
  }
}
