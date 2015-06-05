package com.clouway.http;

/**
 * Created byivan.genchev1989@gmail.com.
 */
class TransactionDTO {

  public Double amount;
  public String transactionType;

  public TransactionDTO() {
  }

  public TransactionDTO(Double amount, String transactionType) {
    this.amount = amount;
    this.transactionType = transactionType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TransactionDTO that = (TransactionDTO) o;

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
