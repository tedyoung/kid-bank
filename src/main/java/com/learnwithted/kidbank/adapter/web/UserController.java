package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.UserProfile;
import com.learnwithted.kidbank.domain.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

  private final UserProfileRepository userProfileRepository;

  @Autowired
  public UserController(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
  }

  @GetMapping("/users")
  public String viewAllUsers(Model model) {
    List<UserProfile> userProfiles = userProfileRepository.findAll();

    List<UserProfileView> userProfileViews = userProfiles.stream()
                                                         .map(UserProfileView::of)
                                                         .collect(Collectors.toList());
    model.addAttribute("userProfiles", userProfileViews);

    return "users";
  }

  @PostMapping("/users/welcome")
  public String sendWelcome() {
    return "";
  }

}
