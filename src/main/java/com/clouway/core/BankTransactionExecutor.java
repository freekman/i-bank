package com.clouway.core;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import java.math.BigDecimal;

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
  public Transaction execute(BigDecimal amount, String query) {
    Optional<User> user = currentUser.get();

    if (user.isPresent() && query.equals(TransactionTypes.withdraw.get())) {
      BigDecimal newAmount = user.get().getAmount().subtract(amount);
      userRegister.updateAmount(user.get().session.sessionId, newAmount.doubleValue());

      return new Transaction(newAmount, "Withdraw");
    } else if (user.isPresent() && query.equals(TransactionTypes.deposit.get())) {
      BigDecimal newAmount = amount.add(user.get().getAmount()) ;
      userRegister.updateAmount(user.get().session.sessionId, newAmount.doubleValue());
      return new Transaction(newAmount, "Deposit");
    }
    return null;
  }
}

