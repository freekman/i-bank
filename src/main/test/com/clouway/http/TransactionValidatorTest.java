package com.clouway.http;

import com.clouway.core.BankUser;
import com.clouway.core.Session;
import com.clouway.core.Transaction;
import com.clouway.core.User;
import com.clouway.validator.TransactionValidator;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TransactionValidatorTest {
  private TransactionValidator validator;
  private BankUser bankUser;
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    bankUser = context.mock(BankUser.class);
    validator = new TransactionValidator(bankUser);
  }

  @Test
  public void withdrawAmountGreaterTheBalance() throws Exception {
    currentUserExpectations();
    boolean isValid = validator.isValid(new Transaction(60.0, "withdraw"));
    assertThat(isValid, is(false));
  }


  @Test
  public void depositWithPositiveAmount() throws Exception {
    currentUserExpectations();
    boolean isValid = validator.isValid(new Transaction(12.0, "deposit"));
    assertTrue(isValid);
  }

  @Test
  public void depositWithNegativeAmount() throws Exception {
    currentUserExpectations();
    boolean isValid = validator.isValid(new Transaction(-12.0, "deposit"));
    assertFalse(isValid);
  }

  @Test
  public void withdrawWithPositiveAmount() throws Exception {
    currentUserExpectations();
    boolean isValid = validator.isValid(new Transaction(12.0, "withdraw"));
    assertTrue(isValid);
  }

  @Test
  public void withdrawWithNegativeAmount() throws Exception {
    currentUserExpectations();
    boolean isValid = validator.isValid(new Transaction(-112.0, "withdraw"));
    assertFalse(isValid);
  }

  private void currentUserExpectations() {
    final User user = new User("Ivan", "", 50.0, new Session("", "", 1l));
    context.checking(new Expectations() {{
      oneOf(bankUser).get();
      will(returnValue(user));
    }});
  }
}