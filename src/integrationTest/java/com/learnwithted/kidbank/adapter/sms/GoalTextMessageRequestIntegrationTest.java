package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.IntegrationTestConfiguration;
import com.learnwithted.kidbank.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(IntegrationTestConfiguration.class)
public class GoalTextMessageRequestIntegrationTest {

  public static final String KID_PHONE_NUMBER = "+14085551212";
  @Autowired
  Account account;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserProfileRepository userProfileRepository;

  public void whenNoGoalsDefinedReturnSorryMessage() throws Exception {
    mockMvc.perform(post("/api/sms")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("Body", "goals")
                        .param("From", KID_PHONE_NUMBER)
                        .param("MessageSid", "98765"))
           .andExpect(status().isOk())
           .andExpect(content().string("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response>" +
                                           "<Message><Body>" +
                                           "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><Message><Body>Sorry, you don't have any goals.</Body></Message></Response>" +
                                           "</Body></Message></Response>"));
  }

  @Test
  public void goalCommandReturnsGoalStatusMessage() throws Exception {
    account.deposit(TestClockSupport.localDateTimeAtMidnightOf(2019, 2, 3), 35_00, "Initial balance.", new DummyUserProfile());

    account.createGoal("\"Nintendo Game: Our World Is Ended\"", 65_39);

    PhoneNumber parentPhoneNumber = new PhoneNumber(KID_PHONE_NUMBER);
    when(userProfileRepository.findByPhoneNumber(parentPhoneNumber))
        .thenReturn(Optional.of(new UserProfile("The Kid", parentPhoneNumber,
                                                "kid@example.com", Role.KID)));

    mockMvc.perform(post("/api/sms")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("Body", "goals")
                        .param("From", KID_PHONE_NUMBER)
                        .param("MessageSid", "98765"))
           .andExpect(status().isOk())
           .andExpect(content().string("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response>" +
                                           "<Message><Body>" +
                                           "Your goal is \"Nintendo Game: Our World Is Ended\", which is $65.39, and you have $35.00 saved (54% to your goal) and you need $30.39 more to buy it." +
                                           "</Body></Message></Response>"));
  }

}
