package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Transaction;
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
  public String deposit(TransactionCommand depositCommand) {
    int depositAmount = depositCommand.amountInCents();
    LocalDateTime dateTime = depositCommand.dateAsLocalDateTime();

    account.deposit(dateTime, depositAmount, depositCommand.getDescription());
    return "redirect:/";
  }

  @PostMapping("/spend")
  public String spend(TransactionCommand spendCommand) {
    int spendAmount = spendCommand.amountInCents();
    LocalDateTime dateTime = spendCommand.dateAsLocalDateTime();

    account.spend(dateTime, spendAmount, spendCommand.getDescription());

    return "redirect:/";
  }

  @PostMapping("/import")
  public String importCsv(ImportCommand importCommand) {
    List<Transaction> transactions = new CsvImporter().importFrom(importCommand.asLines());
    account.load(transactions);
    return "redirect:/";
  }

  @GetMapping("/")
  public String viewBalance(Model model) {
    int balance = account.balance();

    model.addAttribute("balance", TransactionView.formatAsMoney(balance));

    List<TransactionView> transactionViews = account.transactions().stream()
                                                    .sorted(comparing(Transaction::dateTime).reversed())
                                                    .map(TransactionView::from)
                                                    .collect(Collectors.toList());
    model.addAttribute("transactions", transactionViews);

    TransactionCommand depositCommand = TransactionCommand.createWithTodayDate();
    model.addAttribute("deposit", depositCommand);

    TransactionCommand spendCommand = TransactionCommand.createWithTodayDate();
    model.addAttribute("spend", spendCommand);

    model.addAttribute("import", new ImportCommand());

    return "account-balance";
  }
}
