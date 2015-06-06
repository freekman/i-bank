package com.clouway.http;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * Created byivan.genchev1989@gmail.com.
 */
class TransactionDTO {

  @NotNull
  @Pattern(regexp = "/^[1-9]\\d*$/", message = "Invalid number")
  public BigDecimal amount;
  @NotBlank
  public String transactionType;

  public TransactionDTO() {
  }

  public TransactionDTO(BigDecimal amount, String transactionType) {
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
