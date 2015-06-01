package com.clouway.http;

import com.clouway.core.BankUser;
import com.clouway.core.Session;
import com.clouway.core.User;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.clouway.matshers.ReplyContainsObject.contains;
import static com.clouway.matshers.ReplyStatus.statusIs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClientInformationTest {

  private ClientInformation info;
  private BankUser bankUser;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    bankUser = context.mock(BankUser.class);
    info = new ClientInformation(bankUser);

  }

  @Test
  public void deposit() throws Exception {
    context.checking(new Expectations() {{
      oneOf(bankUser).get();
      will(returnValue(new User("Ivan", "qqq", 12.00, new Session("asd", "Ivan", 0l))));
    }});
    Reply<?> reply = info.sendInfo();
    assertThat(reply, is(contains(new UserDTO("Ivan", "qqq", 12.00))));
    assertThat(reply, is(statusIs(200)));
  }

  @Test
  public void sidNotFound() throws Exception {
    context.checking(new Expectations() {{
      oneOf(bankUser).get();
      will(returnValue(null));
    }});
    Reply<?> reply = info.sendInfo();
    assertThat(reply, is(contains("Sid not found!")));
    assertThat(reply, is(statusIs(404)));
  }

}