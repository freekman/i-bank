package com.clouway.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface TransactionHandler {

  Transaction withdraw(Double amount);

  Transaction deposit(Double amount);
}
