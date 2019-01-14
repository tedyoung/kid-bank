package com.learnwithted.kidbank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  @GetMapping("/")
  public String viewBalance() {
    return "$0.00";
  }

}
