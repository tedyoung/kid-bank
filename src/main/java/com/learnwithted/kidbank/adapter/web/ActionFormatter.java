package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Action;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ActionFormatter {
  public static String format(Action action) {
    return Arrays.stream(action.toString().toLowerCase().split("_"))
        .map(StringUtils::capitalize)
        .collect(Collectors.joining(" "));
  }
}
