package com.clouway.core;

import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BankTransactionExecutorTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private BankTransactionExecutor manager;
  private CurrentUser currentUser;
  private AccountRegister accountRegister;

  @Before
  public void setUp() throws Exception {
    accountRegister = context.mock(AccountRegister.class);
    currentUser = context.mock(CurrentUser.class);
    manager = new BankTransactionExecutor(currentUser, accountRegister);
  }

  @Test
  public void deposit() throws Exception {
    final User user = new User("ivan", "qwe", 22.0, new Session("abc", "ivan", 12l));

    context.checking(new Expectations() {{
      oneOf(currentUser).get();
      will(returnValue(Optional.of(user)));
      oneOf(accountRegister).updateAmount(user.session.sessionId, 32.0);

    }});
    Transaction transaction = manager.execute(10.0, "deposit");
    assertThat(transaction.getAmount(), is(32.0));
    assertThat(transaction.getTransactionType(), is("Deposit"));
  }

  @Test
  public void withdraw() throws Exception {
    final User user = new User("ivan", "qwe", 22.0, new Session("abc", "ivan", 12l));

    context.checking(new Expectations() {{
      oneOf(currentUser).get();
      will(returnValue(Optional.of(user)));
      oneOf(accountRegister).updateAmount(user.session.sessionId, 12.0);

    }});
    Transaction transaction = manager.execute(10.0, "withdraw");
    assertThat(transaction.getAmount(), is(12.0));
    assertThat(transaction.getTransactionType(), is("Withdraw"));
  }

}