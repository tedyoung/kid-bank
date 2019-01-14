package com.learnwithted.kidbank;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

  @GetMapping("/")
  public String viewBalance(Model model) {
    Account account = new Account();

    model.addAttribute("balance", account.balance());
    return "account-balance";
  }

  public String formatAsMoney(int amount) {
    return "$0.00";
  }

}
