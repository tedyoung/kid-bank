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
@RequestMapping("/spend")
public class SpendController {
  private final Account account;

  @Autowired
  public SpendController(Account account) {
    this.account = account;
  }

  @GetMapping
  public String spendForm(Model model) {
    int balance = account.balance();
    model.addAttribute("balance", ScaledDecimals.formatAsMoney(balance));

    TransactionCommand spendCommand = TransactionCommand.createWithTodayDate();
    model.addAttribute("spend", spendCommand);

    return "spend";
  }

  @PostMapping
  public String processSpendCommand(TransactionCommand spendCommand) {
    int spendAmount = spendCommand.amountInCents();
    LocalDateTime dateTime = spendCommand.dateAsLocalDateTime();

    account.spend(dateTime, spendAmount, spendCommand.getDescription());

    return "redirect:/";
  }

}
