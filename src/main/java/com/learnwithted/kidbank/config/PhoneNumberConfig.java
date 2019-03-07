package com.learnwithted.kidbank.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sms.phone.number")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberConfig {
  private String parent;
  private String kid;
}
