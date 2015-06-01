package com.clouway.http;

import com.clouway.core.Session;
import com.clouway.core.SidProvider;
import com.clouway.core.User;
import com.clouway.core.UserFinder;
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
  private UserFinder userFinder;
  private SidProvider sidProvider;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    userFinder = context.mock(UserFinder.class);
    sidProvider = context.mock(SidProvider.class);
    info = new ClientInformation(userFinder, sidProvider);

  }

  @Test
  public void deposit() throws Exception {
    context.checking(new Expectations() {{
      oneOf(sidProvider).get();
      will(returnValue("asd"));
      oneOf(userFinder).findBySidIfExist("asd");
      will(returnValue(new User("Ivan", "qqq", 12.00,new Session("asd","Ivan",0l))));
    }});
    Reply<?> reply = info.sendInfo();
    assertThat(reply, is(contains(new UserDTO("Ivan", "qqq", 12.00))));
    assertThat(reply, is(statusIs(200)));
  }

  @Test
  public void sidNotFound() throws Exception {
    context.checking(new Expectations() {{
      oneOf(sidProvider).get();
      will(returnValue(null));
      never(userFinder).findBySidIfExist("asd");
    }});
    Reply<?> reply = info.sendInfo();
    assertThat(reply, is(contains("Sid not found!")));
    assertThat(reply, is(statusIs(404)));
  }

}