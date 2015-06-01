package com.clouway.http;

import com.clouway.core.TransactionManager;
import com.clouway.core.Transaction;
import com.clouway.validator.SimpleValidator;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Post;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/bank")
@Service
public class BankService {
  private final SimpleValidator<Transaction> transactionValidator;
  private final TransactionManager transactionManager;

  @Inject
  public BankService(SimpleValidator transactionValidator, TransactionManager transactionManager) {
    this.transactionValidator = transactionValidator;
    this.transactionManager = transactionManager;
  }

  @Post
  public Reply<?> executeTransaction(Request request) {Re
    TransactionDTO dto = request.read(TransactionDTO.class).as(Json.class);
    if (null == dto) {
      return Reply.with("Transaction did not occur").status(400);
    }
    Transaction transaction = new Transaction(dto.getAmount(), dto.transactionType);
    Transaction result = null;
    boolean transactionIsValid = transactionValidator.isValid(transaction);
    if (transactionIsValid && dto.getTransactionType().equals("deposit")) {
      result = transactionManager.deposit(dto.getAmount());
      return Reply.with(result).as(Json.class).status(200);
    } else if (transactionIsValid && dto.getTransactionType().equals("withdraw")) {
      result = transactionManager.withdraw(dto.getAmount());
      return Reply.with(result).as(Json.class).status(200);
    }
    return Reply.with("Transaction did not occur").status(400);
  }

}
