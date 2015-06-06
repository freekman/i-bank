package com.clouway.core;

import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

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
    final User user = new User("ivan", "qwe", new BigDecimal(22), new Session("abc", "ivan", 12l));

    context.checking(new Expectations() {{
      oneOf(currentUser).get();
      will(returnValue(Optional.of(user)));
      oneOf(accountRegister).updateAmount(user.session.sessionId, 32.0);

    }});
    Transaction transaction = manager.execute(new BigDecimal(10), "deposit");
    assertThat(transaction.getAmount(), is(new BigDecimal(32)));
    assertThat(transaction.getTransactionType(), is("Deposit"));
  }

  @Test
  public void withdraw() throws Exception {
    final User user = new User("ivan", "qwe", new BigDecimal(22), new Session("abc", "ivan", 12l));

    context.checking(new Expectations() {{
      oneOf(currentUser).get();
      will(returnValue(Optional.of(user)));
      oneOf(accountRegister).updateAmount(user.session.sessionId, 12.0);

    }});
    Transaction transaction = manager.execute(new BigDecimal(10), "withdraw");
    assertThat(transaction.getAmount(), is(new BigDecimal(12)));
    assertThat(transaction.getTransactionType(), is("Withdraw"));
  }

}