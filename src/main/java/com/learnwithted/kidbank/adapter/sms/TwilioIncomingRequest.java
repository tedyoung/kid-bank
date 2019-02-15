package com.learnwithted.kidbank.adapter.sms;

import lombok.Data;

@Data
public class TwilioIncomingRequest {
  private String body;
  private String from;
  private String messageSid;
}
