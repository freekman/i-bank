package com.clouway.http;

import com.clouway.core.Transaction;
import com.clouway.core.TransactionHandler;
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
import static org.hamcrest.MatcherAssert.assertThat;

public class BankServiceDepositTest {

  private BankService bankService;
  private SimpleValidator<Transaction> validator;
  private TransactionHandler transactionHandler;
  private Request request;
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    validator = context.mock(SimpleValidator.class);
    transactionHandler = context.mock(TransactionHandler.class);
    request = context.mock(Request.class);
    bankService = new BankService(validator, transactionHandler);
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
      oneOf(transactionHandler).deposit(10.0);
      will(returnValue(new Transaction(10.0, "ok")));
    }});
    Reply replay = bankService.executeTransaction(request);
    assertThat(replay, contains(new Transaction(10.0, "ok")));
    assertThat(replay, statusIs(200));
  }

  @Test
  public void transactionDTOWithNullAmount() throws Exception {
    context.checking(new Expectations() {{
      oneOf(request).read(TransactionDTO.class);
      will(returnValue(new RequestRead<TransactionDTO>() {
        @Override
        public TransactionDTO as(Class<? extends Transport> transport) {
          return new TransactionDTO(null, "deposit");
        }
      }));
      oneOf(validator).isValid(null);
      will(returnValue(false));
    }});
    Reply replay = bankService.executeTransaction(request);
    assertThat(replay,statusIs(400));
  }

}