package com.clouway.core;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;
import org.junit.Rule;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BankCurrentUserTest {

  private BankCurrentUser currentUser;
  private UserFinder userFinder;
  private SidProvider sidProvider;
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    sidProvider = context.mock(SidProvider.class);
    userFinder = context.mock(UserFinder.class);
    currentUser = new BankCurrentUser(userFinder,sidProvider);
  }

  @Test
  public void happyPath() throws Exception {
    final User result = new User("ivan", "qwe", 22.0, new Session("abc", "ivan", 12l));
    context.checking(new Expectations() {{
      oneOf(sidProvider).get();will(returnValue("abc"));
      oneOf(userFinder).findBySidIfExist("abc");
      will(returnValue(result));
    }});
    User user= currentUser.get();
    assertThat(user,is(result));
  }
  @Test
  public void missingSid() throws Exception {
    context.checking(new Expectations() {{
      oneOf(sidProvider).get();will(returnValue(null));
    }});
    User dto= currentUser.get();
   assertNull(dto);
  }

}