package com.clouway.core;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class BankTransactionExecutor implements TransactionExecutor {

  private final CurrentUser currentUser;
  private AccountRegister userRegister;

  @Inject
  public BankTransactionExecutor(CurrentUser currentUser, AccountRegister userRegister) {
    this.currentUser = currentUser;
    this.userRegister = userRegister;
  }

  @Override
  public Transaction execute(Double amount, String query) {
    Optional<User> user = currentUser.get();

    if (user.isPresent() && query.equals("withdraw")) {
      Double newAmount = user.get().getAmount() - amount;

      userRegister.updateAmount(user.get().session.sessionId, newAmount);

      return new Transaction(newAmount, "Withdraw");
    } else if (user.isPresent() && query.equals("deposit")) {
      Double newAmount = amount + user.get().getAmount();
      userRegister.updateAmount(user.get().session.sessionId, newAmount);

      return new Transaction(newAmount, "Deposit");
    }
    return null;
  }
}

