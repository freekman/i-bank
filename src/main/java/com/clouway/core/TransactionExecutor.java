package com.clouway.core;

import java.math.BigDecimal;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface TransactionExecutor {

  Transaction execute(BigDecimal amount,String query);

}
