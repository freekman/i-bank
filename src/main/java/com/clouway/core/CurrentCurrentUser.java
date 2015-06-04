package com.clouway.core;

import com.google.inject.Inject;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class CurrentCurrentUser implements CurrentUser {

  private UserFinder userFinder;
  private SidProvider sidProvider;

  @Inject
  public CurrentCurrentUser(UserFinder userFinder, SidProvider sidProvider) {
    this.userFinder = userFinder;
    this.sidProvider = sidProvider;
  }

  @Override
  public User get() {
    String sid = sidProvider.get();
    if (null != sid) {
       return userFinder.findBySidIfExist(sid);
    }
    return null;
  }

}
