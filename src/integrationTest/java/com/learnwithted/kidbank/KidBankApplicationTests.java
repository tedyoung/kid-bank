package com.learnwithted.kidbank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KidBankApplicationTests {

  @Autowired
  private TwilioConfig twilioConfig;

  @Test
  public void contextLoads() {
  }

  @Test
  public void twilioConfigLoads() {
    assertThat(twilioConfig.getAccountSid())
        .isNotBlank()
        .isEqualTo("AXXXX");
    assertThat(twilioConfig.getAuthToken())
        .isNotBlank()
        .isEqualTo("12345");
  }
}

