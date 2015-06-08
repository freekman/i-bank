package com.clouway.http;

import com.clouway.core.CurrentUser;
import com.clouway.core.Session;
import com.clouway.core.Transaction;
import com.clouway.core.User;
import com.clouway.validator.TransactionValidator;
import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TransactionValidatorTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private TransactionValidator validator;
  private CurrentUser currentUser;


  @Before
  public void setUp() throws Exception {
    currentUser = context.mock(CurrentUser.class);
    validator = new TransactionValidator(currentUser);
  }


  @Test
  public void withdrawAmountGreaterTheBalance() throws Exception {
    currentUserExpectations();
    boolean isValid = validator.isValid(new Transaction(new BigDecimal(60), "withdraw"));
    assertThat(isValid, is(false));
  }


  @Test
  public void depositWithPositiveAmount() throws Exception {
    currentUserExpectations();
    boolean isValid = validator.isValid(new Transaction(new BigDecimal(12), "deposit"));
    assertTrue(isValid);
  }

  @Test
  public void withdrawWithPositiveAmount() throws Exception {
    currentUserExpectations();
    boolean isValid = validator.isValid(new Transaction(new BigDecimal(12), "withdraw"));
    assertTrue(isValid);
  }

  private void currentUserExpectations() {
    final User user = new User("Ivan", "", new BigDecimal(50), new Session("", "", 1l));
    context.checking(new Expectations() {{
      oneOf(currentUser).get();
      will(returnValue(Optional.of(user)));
    }});
  }

}