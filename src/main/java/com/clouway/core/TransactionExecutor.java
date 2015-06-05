package com.clouway.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface TransactionExecutor {

  Transaction execute(Double amount,String query);

}
