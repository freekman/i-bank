package com.clouway.core;

import java.math.BigDecimal;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class Transaction {
  private BigDecimal amount;
  private String transactionType;

  public Transaction(BigDecimal amount, String transactionType) {
    this.amount = amount;
    this.transactionType = transactionType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getTransactionType() {
    return transactionType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Transaction that = (Transaction) o;

    if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
    if (transactionType != null ? !transactionType.equals(that.transactionType) : that.transactionType != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = amount != null ? amount.hashCode() : 0;
    result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
    return result;
  }
}
