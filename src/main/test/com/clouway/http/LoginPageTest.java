package com.clouway.http;

import com.clouway.core.Authenticator;
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

  private LoginPage loginPage;
  private Authenticator<User> authenticator;
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    authenticator = context.mock(Authenticator.class);
    loginPage = new LoginPage(authenticator);
  }

  @Test
  public void happyPath() throws Exception {
    loginPage.uName = "asa";
    loginPage.pwd = "asa";
    context.checking(new Expectations() {{
      User user = new User("asa", "asa", 0.0,new Session("","",0l));
      oneOf(authenticator).authenticate(user);
      will(returnValue(true));
      oneOf(authenticator).registerSession(user);
    }});
    String redirectTo = loginPage.login();
    assertThat(redirectTo, is("index.html#/welcome"));
  }

  @Test
  public void userNotAuthenticated() throws Exception {
    loginPage.uName = "asa";
    loginPage.pwd = "asa";
    context.checking(new Expectations() {{
      User user = new User("asa", "asa", 0.0,new Session("","",0l));
      oneOf(authenticator).authenticate(user);
      will(returnValue(false));
      never(authenticator).registerSession(user);
    }});
    String redirectTo = loginPage.login();
    assertThat(redirectTo, is("/home"));
  }

}