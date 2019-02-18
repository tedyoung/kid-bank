package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/deposit")
public class DepositController {

  private final Account account;

  @Autowired
  public DepositController(Account account) {
    this.account = account;
  }

  @GetMapping
  public String depositForm(Model model) {
    int balance = account.balance();
    model.addAttribute("balance", ScaledDecimals.formatAsMoney(balance));

    TransactionCommand depositCommand = TransactionCommand.createWithTodayDate();
    model.addAttribute("deposit", depositCommand);

    return "deposit";
  }

  @PostMapping
  public String processDepositCommand(TransactionCommand depositCommand) {
    int depositAmount = depositCommand.amountInCents();
    LocalDateTime dateTime = depositCommand.dateAsLocalDateTime();

    account.deposit(dateTime, depositAmount, depositCommand.getDescription());
    return "redirect:/";
  }

}
