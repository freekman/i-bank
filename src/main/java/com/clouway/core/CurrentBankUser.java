package com.clouway.core;

import com.google.inject.Inject;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class CurrentBankUser implements BankUser {

  private UserFinder userFinder;
  private SidProvider sidProvider;

  @Inject
  public CurrentBankUser(UserFinder userFinder, SidProvider sidProvider) {
    this.userFinder = userFinder;
    this.sidProvider = sidProvider;
  }

  @Override
  public User get() {
    User current = null;
    String sid = sidProvider.get();
    if (null != sid) {
      current = userFinder.findBySidIfExist(sid);
    }
    return current;
  }

}
