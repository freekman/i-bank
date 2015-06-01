package com.clouway.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class User {

  private final String name;
  private final String password;
  private Double amount;
  private final Session session;

  public User(String name, String password, Double amount, Session session) {
    this.name = name;
    this.password = password;
    this.amount = amount;

    this.session = session;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public Double getAmount() {
    return amount;
  }

  public Session getSession() {
    return session;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (amount != null ? !amount.equals(user.amount) : user.amount != null) return false;
    if (name != null ? !name.equals(user.name) : user.name != null) return false;
    if (password != null ? !password.equals(user.password) : user.password != null) return false;
    if (session != null ? !session.equals(user.session) : user.session != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (amount != null ? amount.hashCode() : 0);
    result = 31 * result + (session != null ? session.hashCode() : 0);
    return result;
  }
}
