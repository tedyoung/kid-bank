package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.app.Welcomer;
import com.learnwithted.kidbank.domain.UserProfile;
import com.learnwithted.kidbank.domain.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(UserProfileController.USERS_URL)
public class UserProfileController {

  static final String USERS_URL = "/users";
  private final UserProfileRepository userProfileRepository;
  private final Welcomer welcomer;

  @Autowired
  public UserProfileController(UserProfileRepository userProfileRepository, Welcomer welcomer) {
    this.userProfileRepository = userProfileRepository;
    this.welcomer = welcomer;
  }

  @GetMapping
  public String viewAllUsers(Model model) {
    List<UserProfile> userProfiles = userProfileRepository.findAll();

    List<UserProfileView> userProfileViews = userProfiles.stream()
                                                         .map(UserProfileView::of)
                                                         .collect(Collectors.toList());
    model.addAttribute("userProfiles", userProfileViews);

    return "users";
  }

  @PostMapping("welcome")
  public String sendWelcome(@RequestParam("id") Long profileId) {
    if (profileId == null) {
      throw new IllegalArgumentException("ID was null upon form submission.");
    }
    if (!userProfileRepository.findById(profileId).isPresent()) {
      throw new IllegalArgumentException("Profile ID " + profileId + " does not exist");
    }

    welcomer.welcome(profileId);

    return "redirect:" + AccountController.ACCOUNT_URL;
  }

  @PostMapping("create")
  public String createUserProfile(CreateUserProfile createUserProfile) {
    UserProfile userProfile = createUserProfile.asUserProfile();

    userProfileRepository.save(userProfile);

    return "redirect:" + USERS_URL;
  }

  @GetMapping("create")
  public String getUserCreateForm(Model model) {
    model.addAttribute("createUserProfile", new CreateUserProfile());
    return "create-user";
  }

}
