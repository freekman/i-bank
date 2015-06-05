package com.clouway.validator;

import com.clouway.core.CurrentUser;
import com.clouway.core.Transaction;
import com.clouway.core.User;
import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class TransactionValidator implements SimpleValidator<Transaction> {
  private CurrentUser currentUser;

  @Inject
  public TransactionValidator(CurrentUser currentUser) {
    this.currentUser = currentUser;
  }

  @Override
  public boolean isValid(Transaction transaction) {
    Optional<User> current = currentUser.get();
    if (!current.isPresent()) {
      return false;
    }
    if (transaction == null) {
      return false;
    }
    if (transaction.getTransactionType().equals("deposit") && transaction.getAmount() < 0) {
      return false;
    }
    if (transaction.getTransactionType().equals("withdraw") && transaction.getAmount() < 0) {
      return false;
    }
    if (transaction.getTransactionType().equals("withdraw") && ((current.get().getAmount() - transaction.getAmount()) <= 0)) {
      return false;
    }
    return true;
  }
}
