package com.clouway.http;

/**
 * Created byivan.genchev1989@gmail.com.
 */
class StatusTransactionDTO {
  public Double amount;
  public String status;

  public StatusTransactionDTO() {
  }

  public StatusTransactionDTO(Double amount, String status) {
    this.amount = amount;
    this.status = status;
  }

  public Double getAmount() {
    return amount;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    StatusTransactionDTO that = (StatusTransactionDTO) o;

    if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
    if (status != null ? !status.equals(that.status) : that.status != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = amount != null ? amount.hashCode() : 0;
    result = 31 * result + (status != null ? status.hashCode() : 0);
    return result;
  }
}
