package com.clouway.http;

import com.clouway.core.Transaction;
import com.clouway.core.TransactionHandler;
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
  private final TransactionHandler transactionHandler;

  @Inject
  public BankService(SimpleValidator transactionValidator, TransactionHandler transactionHandler) {
    this.transactionValidator = transactionValidator;
    this.transactionHandler = transactionHandler;
  }

  @Post
  public Reply<?> executeTransaction(Request request) {
    TransactionDTO dto = request.read(TransactionDTO.class).as(Json.class);
    Transaction transaction=null;
    if (null != dto && null != dto.amount) {
      transaction = new Transaction(dto.amount, dto.transactionType);
    }
    Transaction result = null;
    boolean transactionIsValid = transactionValidator.isValid(transaction);
    if (transactionIsValid && dto.getTransactionType().equals("deposit")) {
      result = transactionHandler.deposit(dto.getAmount());
      return Reply.with(result).as(Json.class).status(200);
    } else if (transactionIsValid && dto.getTransactionType().equals("withdraw")) {
      result = transactionHandler.withdraw(dto.getAmount());
      return Reply.with(result).as(Json.class).status(200);
    }
    return Reply.with("Transaction did not occur").status(400);
  }

}
