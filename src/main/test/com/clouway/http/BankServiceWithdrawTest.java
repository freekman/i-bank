package com.clouway.http;

import com.clouway.core.TransactionExecutor;
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

public class BankServiceWithdrawTest {
  private BankService bankService;
  private SimpleValidator<Transaction> validator;
  private TransactionExecutor transactionExecutor;
  private Request rq;
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    validator = context.mock(SimpleValidator.class);
    transactionExecutor = context.mock(TransactionExecutor.class);
    rq = context.mock(Request.class);
    bankService = new BankService(validator, transactionExecutor);
  }

  @Test
  public void happyPath() throws Exception {
    context.checking(new Expectations() {{
      oneOf(rq).read(TransactionDTO.class);
      will(returnValue(new RequestRead<TransactionDTO>() {
        @Override
        public TransactionDTO as(Class<? extends Transport> transport) {
          return new TransactionDTO(10.00, "withdraw");
        }
      }));
      oneOf(validator).isValid(new Transaction(10.0, "withdraw"));
      will(returnValue(true));
      oneOf(transactionExecutor).execute(10.0, "withdraw");
      will(returnValue(new Transaction(10.0, "OK")));
    }});
    Reply replay = bankService.executeTransaction(rq);
    assertThat(replay, is(contains(new Transaction(10.0, "OK"))));
    assertThat(replay, is(statusIs(200)));
  }

  @Test
  public void notValidTransaction() throws Exception {
    context.checking(new Expectations() {{
      oneOf(rq).read(TransactionDTO.class);
      will(returnValue(new RequestRead<TransactionDTO>() {
        @Override
        public TransactionDTO as(Class<? extends Transport> transport) {
          return new TransactionDTO(10.00, "withdraw");
        }
      }));
      oneOf(validator).isValid(new Transaction(10.0, "withdraw"));
      will(returnValue(false));
    }});
    Reply replay = bankService.executeTransaction(rq);
    assertThat(replay, is(statusIs(400)));
  }

}