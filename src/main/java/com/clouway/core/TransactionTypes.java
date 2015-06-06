package com.clouway.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public enum TransactionTypes {
  withdraw("withdraw"),
  deposit("deposit");

  private String value;

  TransactionTypes(String value) {
    this.value = value;
  }

  public String get() {
    return value;
  }
}
