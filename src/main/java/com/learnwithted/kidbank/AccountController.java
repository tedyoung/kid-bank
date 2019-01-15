package com.learnwithted.kidbank;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Collections;

@Controller
public class AccountController {
  public static final DecimalFormat FORMAT_AS_DOLLARS_AND_CENTS = new DecimalFormat("$###,##0.00");

  private final Account account;


  public AccountController(Account account) {
    this.account = account;
  }

  @GetMapping("/")
  public String viewBalance(Model model) {
    int balance = account.balance();
    model.addAttribute("balance", formatAsMoney(balance));
    model.addAttribute("deposit", new DepositCommand());
    model.addAttribute("transactions", Collections.singleton(new TransactionView("01/05/2005", "Cash Deposit", "$12.45", "Birthday gift")));
    return "account-balance";
  }

  @PostMapping("/deposit")
  public String deposit(DepositCommand depositCommand) {
    int depositAmount = depositCommand.amountInCents();
    account.deposit(LocalDateTime.now(), depositAmount, "tbd");
    return "redirect:/";
  }

  public String formatAsMoney(int amount) {
    BigDecimal amountInDollars = new BigDecimal(amount).scaleByPowerOfTen(-2);
    return FORMAT_AS_DOLLARS_AND_CENTS.format(amountInDollars);
  }

  public String viewBalance() {
    return formatAsMoney(account.balance());
  }
}
