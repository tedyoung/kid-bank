package com.learnwithted.kidbank;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

  @GetMapping("/")
  public String viewBalance(Model model) {
    Account account = new Account();

    int balance = account.balance();
    model.addAttribute("balance", formatAsMoney(balance));
    return "account-balance";
  }

  public String formatAsMoney(int amount) {
    return "$0.00";
  }

}
