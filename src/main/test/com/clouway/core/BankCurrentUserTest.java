package com.clouway.core;

import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BankCurrentUserTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private BankCurrentUser currentUser;
  private UserFinder userFinder;
  private SidProvider sidProvider;

  @Before
  public void setUp() throws Exception {
    sidProvider = context.mock(SidProvider.class);
    userFinder = context.mock(UserFinder.class);
    currentUser = new BankCurrentUser(userFinder, sidProvider);
  }

  @Test
  public void happyPath() throws Exception {
    final User result = new User("ivan", "qwe", new BigDecimal(22), new Session("abc", "ivan", 12l));
    context.checking(new Expectations() {{
      oneOf(sidProvider).get();
      will(returnValue(Optional.of("abc")));
      oneOf(userFinder).findBySidIfExist("abc");
      will(returnValue(Optional.of(result)));
    }});
    Optional<User> user = currentUser.get();
    assertThat(user.get(), is(result));
  }

  @Test
  public void missingSid() throws Exception {
    context.checking(new Expectations() {{
      oneOf(sidProvider).get();
      will(returnValue(Optional.absent()));
      never(userFinder).findBySidIfExist(null);
      will(returnValue(null));
    }});
    Optional<User> dto = currentUser.get();
    assertThat(dto.isPresent(), is(false));
  }

}