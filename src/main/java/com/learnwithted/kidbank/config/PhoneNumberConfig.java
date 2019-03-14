package com.learnwithted.kidbank.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Configuration
@ConfigurationProperties(prefix = "sms.phone.number")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class PhoneNumberConfig {
  @NotEmpty
  private String parent;
  @NotEmpty
  private String kid;
}
