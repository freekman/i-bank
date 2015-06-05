package com.clouway.http;

import com.clouway.core.Session;
import com.clouway.core.SessionRegister;
import com.clouway.core.User;
import com.clouway.core.UserFinder;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankUserAuthenticatorAuthenticateTest {

  private BankUserAuthenticator bankUserAuthenticator;
  private UserFinder userFinder;
  private SessionRegister sessionRegister;
  private Provider prov;
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    userFinder = context.mock(UserFinder.class);
    sessionRegister = context.mock(SessionRegister.class);
    bankUserAuthenticator = new BankUserAuthenticator(userFinder, sessionRegister, prov, prov);

  }

  @Test
  public void happyPath() throws Exception {
    final User user = new User("Ivan", "qwerty", 0.0, new Session("", "Ivan", 0l));
    context.checking(new Expectations() {{
      oneOf(userFinder).findByName("Ivan");
      will(returnValue(user));
    }});
    boolean result = bankUserAuthenticator.authenticate(user);
    assertTrue(result);
  }

  @Test
  public void wrongName() throws Exception {
    final User user = new User("Iva", "qwerty", 0.0, new Session("", "Ivan", 0l));
    context.checking(new Expectations() {{
      oneOf(userFinder).findByName("Ivan");
      will(returnValue(user));
    }});
    boolean result = bankUserAuthenticator.authenticate(new User("Ivan", "qwerty", 0.0, new Session("", "Ivan", 0l)));
    assertFalse(result);
  }

  @Test
  public void wrongPassword() throws Exception {
    final User user = new User("Ivan", "qwe", 0.0, new Session("", "Ivan", 0l));
    context.checking(new Expectations() {{
      oneOf(userFinder).findByName("Ivan");
      will(returnValue(user));
    }});
    boolean result = bankUserAuthenticator.authenticate(new User("Ivan", "qwerty", 0.0, new Session("", "Ivan", 0l)));
    assertFalse(result);
  }

}