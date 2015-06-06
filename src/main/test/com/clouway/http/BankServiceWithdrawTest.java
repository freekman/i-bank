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

import java.math.BigDecimal;

import static com.clouway.matchers.ReplyContainsObject.contains;
import static com.clouway.matchers.ReplyStatus.statusIs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BankServiceWithdrawTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private BankService bankService;
  private SimpleValidator<Transaction> validator;
  private TransactionExecutor transactionExecutor;
  private Request rq;


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
          return new TransactionDTO(new BigDecimal(10), "withdraw");
        }
      }));
      oneOf(validator).isValid(new Transaction(new BigDecimal(10), "withdraw"));
      will(returnValue(true));
      oneOf(transactionExecutor).execute(new BigDecimal(10), "withdraw");
      will(returnValue(new Transaction(new BigDecimal(10), "OK")));
    }});
    Reply replay = bankService.executeTransaction(rq);
    assertThat(replay, is(contains(new Transaction(new BigDecimal(10), "OK"))));
    assertThat(replay, is(statusIs(200)));
  }

  @Test
  public void notValidTransaction() throws Exception {
    context.checking(new Expectations() {{
      oneOf(rq).read(TransactionDTO.class);
      will(returnValue(new RequestRead<TransactionDTO>() {
        @Override
        public TransactionDTO as(Class<? extends Transport> transport) {
          return new TransactionDTO(new BigDecimal(10), "withdraw");
        }
      }));
      oneOf(validator).isValid(new Transaction(new BigDecimal(10), "withdraw"));
      will(returnValue(false));
    }});
    Reply replay = bankService.executeTransaction(rq);
    assertThat(replay, is(statusIs(400)));
  }

}