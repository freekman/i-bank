package com.clouway.validator;

import com.clouway.core.BankUser;
import com.clouway.core.Transaction;
import com.clouway.core.User;
import com.google.inject.Inject;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class TransactionValidator implements SimpleValidator<Transaction> {
  private BankUser bankUser;

  @Inject
  public TransactionValidator(BankUser bankUser) {
    this.bankUser = bankUser;
  }

  @Override
  public boolean isValid(Transaction transaction) {
    User current = bankUser.get();
    if (transaction.getTransactionType().equals("deposit") && transaction.getAmount() < 0) {
      return false;
    }
    if (transaction.getTransactionType().equals("withdraw") && transaction.getAmount() < 0) {
      return false;
    }
    if (transaction.getTransactionType().equals("withdraw") && ((current.getAmount() - transaction.getAmount()) <= 0)) {
      return false;
    }
    return true;
  }
}
