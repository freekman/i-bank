package com.clouway.http;

import com.clouway.core.TransactionManager;
import com.clouway.core.Transaction;
import com.clouway.validator.SimpleValidator;
import com.google.sitebricks.client.Transport;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Request.RequestRead;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.clouway.matshers.ReplyContainsObject.contains;
import static com.clouway.matshers.ReplyStatus.statusIs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BankServiceDepositTest {

  private BankService bankService;
  private SimpleValidator<Transaction> validator;
  private TransactionManager transactionManager;
  private Request request;
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    validator = context.mock(SimpleValidator.class);
    transactionManager = context.mock(TransactionManager.class);
    request = context.mock(Request.class);
    bankService = new BankService(validator, transactionManager);
  }

  @Test
  public void happyPath() throws Exception {
    context.checking(new Expectations() {{
      oneOf(request).read(TransactionDTO.class);
      will(returnValue(new RequestRead<TransactionDTO>() {
        @Override
        public TransactionDTO as(Class<? extends Transport> transport) {
          return new TransactionDTO(10.00, "deposit");
        }
      }));
      oneOf(validator).isValid(new Transaction(10.0, "deposit"));
      will(returnValue(true));
      oneOf(transactionManager).deposit(10.0);
      will(returnValue(new Transaction(10.0, "ok")));
    }});
    Reply replay = bankService.executeTransaction(request);
    assertThat(replay, is(contains(new Transaction(10.0, "ok"))));
    assertThat(replay, is(statusIs(200)));
  }

}