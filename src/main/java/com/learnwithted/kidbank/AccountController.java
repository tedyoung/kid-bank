package com.learnwithted.kidbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Controller
public class AccountController {

  private final Account account;

  @Autowired
  public AccountController(Account account) {
    this.account = account;
  }

  @PostMapping("/deposit")
  public String deposit(DepositCommand depositCommand) {
    int depositAmount = depositCommand.amountInCents();
    LocalDateTime dateTime = depositCommand.dateAsLocalDateTime();
    account.deposit(dateTime, depositAmount, depositCommand.getSource());
    return "redirect:/";
  }

  @GetMapping("/")
  public String viewBalance(Model model) {
    int balance = account.balance();
    model.addAttribute("balance", TransactionView.formatAsMoney(balance));
    DepositCommand depositCommand = DepositCommand.createWithTodayDate();
    model.addAttribute("deposit", depositCommand);
    List<TransactionView> transactionViews = account.transactions().stream()
                                                    .sorted(comparing(Transaction::dateTime).reversed())
                                                    .map(TransactionView::from)
                                                    .collect(Collectors.toList());
    model.addAttribute("transactions", transactionViews);
    return "account-balance";
  }

}
