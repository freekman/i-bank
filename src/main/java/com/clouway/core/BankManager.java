package com.clouway.core;

import com.google.inject.Inject;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class BankManager implements Manager {

  private final BankUser bankUser;
  private final UserRegister userRegister;

  @Inject
  public BankManager(BankUser bankUser, UserRegister userRegister) {
    this.bankUser = bankUser;
    this.userRegister = userRegister;
  }

  @Override
  public Transaction withdraw(Double amount) {
    User user = bankUser.get();
    if (null != user) {
      Double newAmount = user.getAmount() - amount;
      userRegister.updateAmount(user.getSession().getSessionId(), newAmount);
      User userAfterTransaction = bankUser.get();
      return new Transaction(userAfterTransaction.getAmount(), "Withdraw");
    }
    return null;
  }

  @Override
  public Transaction deposit(Double amount) {
    User user = bankUser.get();
    if (null != user) {
      Double newAmount = amount + user.getAmount();
      userRegister.updateAmount(user.getSession().getSessionId(), newAmount);
      User userAfterTransaction = bankUser.get();
      return new Transaction(userAfterTransaction.getAmount(), "Deposit");
    }
    return null;
  }
}

