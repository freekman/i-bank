package com.clouway.core;

import com.google.inject.Inject;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class BankTransactionManager implements TransactionManager {

  private final CurrentUser currentUser;
  private final UserRegister userRegister;

  @Inject
  public BankTransactionManager(CurrentUser currentUser, UserRegister userRegister) {
    this.currentUser = currentUser;
    this.userRegister = userRegister;
  }

  @Override
  public Transaction withdraw(Double amount) {
    User user = currentUser.get();
    if (null != user) {
      Double newAmount = user.getAmount() - amount;
      userRegister.updateAmount(user.getSession().getSessionId(), newAmount);
      User userAfterTransaction = currentUser.get();
      return new Transaction(userAfterTransaction.getAmount(), "Withdraw");
    }
    return null;
  }

  @Override
  public Transaction deposit(Double amount) {
    User user = currentUser.get();
    if (null != user) {
      Double newAmount = amount + user.getAmount();
      userRegister.updateAmount(user.getSession().getSessionId(), newAmount);
      User userAfterTransaction = currentUser.get();
      return new Transaction(userAfterTransaction.getAmount(), "Deposit");
    }
    return null;
  }
}

