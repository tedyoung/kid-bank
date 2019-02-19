package com.learnwithted.kidbank.adapter.sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "KNOWN_PHONE_NUMBER=+14085551212")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "parent", roles = {"KID", "PARENT"})
public class SmsBalanceCheckIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void fromTwilioViaKnownNumberShouldRespondWithCurrentBalance() throws Exception {
    mockMvc.perform(post("/deposit")
                        .param("amount", "98.95")
                        .param("date", "2019-02-14")
                        .param("description", "the source of money"));

    String knownNumber = "+14085551212";
    mockMvc.perform(post("/api/sms")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("Body", "balance")
                        .param("From", knownNumber)
                        .param("MessageSid", "123435"))
           .andExpect(status().isOk())
           .andExpect(content().string("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><Message><Body>Your balance is $98.95</Body></Message></Response>"));
  }

}
