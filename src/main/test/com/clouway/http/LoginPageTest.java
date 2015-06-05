package com.clouway.http;

import com.clouway.core.UserAuthenticator;
import com.clouway.core.Session;
import com.clouway.core.User;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LoginPageTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private LoginPage loginPage;
  private UserAuthenticator userAuthenticator;

  @Before
  public void setUp() throws Exception {
    userAuthenticator = context.mock(UserAuthenticator.class);
    loginPage = new LoginPage(userAuthenticator);
  }

  @Test
  public void happyPath() throws Exception {
    loginPage.userName = "asa";
    loginPage.password = "asa";
    context.checking(new Expectations() {{
      User user = new User("asa", "asa", 0.0,new Session("","",0l));
      oneOf(userAuthenticator).authenticate(user);
      will(returnValue(true));
      oneOf(userAuthenticator).registerSession(user);
    }});
    String redirectTo = loginPage.login();
    assertThat(redirectTo, is("index.html#/welcome"));
  }

  @Test
  public void userNotAuthenticated() throws Exception {
    loginPage.userName = "asa";
    loginPage.password = "asa";
    context.checking(new Expectations() {{
      User user = new User("asa", "asa", 0.0,new Session("","",0l));
      oneOf(userAuthenticator).authenticate(user);
      will(returnValue(false));
      never(userAuthenticator).registerSession(user);
    }});
    String redirectTo = loginPage.login();
    assertThat(redirectTo, is("/home"));
  }

}