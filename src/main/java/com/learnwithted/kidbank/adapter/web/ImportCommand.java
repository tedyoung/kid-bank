package com.learnwithted.kidbank.adapter.web;

import com.google.common.base.Splitter;
import lombok.Data;

import java.util.List;
import java.util.regex.Pattern;

@Data
public class ImportCommand {
  public static final Pattern NEW_LINE_SEPARATOR_PATTERN = Pattern.compile("\r?\n");
  private String content;

  public List<String> asLines() {
    return Splitter.on(NEW_LINE_SEPARATOR_PATTERN)
                   .omitEmptyStrings()
                   .splitToList(content);
  }
}
