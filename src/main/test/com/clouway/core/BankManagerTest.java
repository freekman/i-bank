package com.clouway.core;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;
import org.junit.Rule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BankManagerTest {

  private BankManager manager;
  private BankUser bankUser;
  private UserRegister userRegister;
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    userRegister = context.mock(UserRegister.class);
    bankUser = context.mock(BankUser.class);
    manager = new BankManager(bankUser, userRegister);
  }

  @Test
  public void deposit() throws Exception {
    final User user = new User("ivan", "qwe", 22.0, new Session("abc", "ivan", 12l));
    final User afterTransaction = new User("ivan", "qwe", 32.0, new Session("abc", "ivan", 12l));
    context.checking(new Expectations() {{
      oneOf(bankUser).get();
      will(returnValue(user));
      oneOf(userRegister).updateAmount(user.getSession().getSessionId(), 32.0);
      oneOf(bankUser).get();will(returnValue(afterTransaction));
    }});
    Transaction transaction=manager.deposit(10.0);
    assertThat(transaction.getAmount(),is(32.0));
    assertThat(transaction.getTransactionType(),is("Deposit"));
  }
  @Test
  public void withdraw() throws Exception {
    final User user = new User("ivan", "qwe", 22.0, new Session("abc", "ivan", 12l));
    final User aftherTransaction = new User("ivan", "qwe", 12.0, new Session("abc", "ivan", 12l));
    context.checking(new Expectations() {{
      oneOf(bankUser).get();
      will(returnValue(user));
      oneOf(userRegister).updateAmount(user.getSession().getSessionId(), 12.0);
      oneOf(bankUser).get();will(returnValue(aftherTransaction));
    }});
    Transaction transaction=manager.withdraw(10.0);
    assertThat(transaction.getAmount(),is(12.0));
    assertThat(transaction.getTransactionType(),is("Withdraw"));
  }

}