package com.clouway.core;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class BankTransactionHandler implements TransactionHandler {

  private final CurrentUser currentUser;
  private final UserRegister userRegister;

  @Inject
  public BankTransactionHandler(CurrentUser currentUser, UserRegister userRegister) {
    this.currentUser = currentUser;
    this.userRegister = userRegister;
  }

  @Override
  public Transaction withdraw(Double amount) {
    Optional<User> user = currentUser.get();
    if (user.isPresent()) {
      Double newAmount = user.get().getAmount() - amount;
      userRegister.updateAmount(user.get().getSession().getSessionId(), newAmount);
      User userAfterTransaction = currentUser.get().get();
      return new Transaction(userAfterTransaction.getAmount(), "Withdraw");
    }
    return null;
  }

  @Override
  public Transaction deposit(Double amount) {
    Optional<User> user = currentUser.get();
    if (user.isPresent()) {
      Double newAmount = amount + user.get().getAmount();
      userRegister.updateAmount(user.get().getSession().getSessionId(), newAmount);
      User userAfterTransaction = currentUser.get().get();
      return new Transaction(userAfterTransaction.getAmount(), "Deposit");
    }
    return null;
  }
}

