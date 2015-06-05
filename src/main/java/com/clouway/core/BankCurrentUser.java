package com.clouway.core;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class BankCurrentUser implements CurrentUser {

  private UserFinder finder;
  private SidProvider provider;

  @Inject
  public BankCurrentUser(UserFinder userFinder, SidProvider provider) {
    this.finder = userFinder;
    this.provider = provider;
  }

  @Override
  public User get() {
    Optional<String> sid = provider.get();
    if (sid.isPresent()) {
      return finder.findBySidIfExist(sid.get());
    }
    return null;
  }

//  @Override
//  public User get() {
//    String sid = provider.get();
//    //if (null != sid) {
//       return finder.findBySidIfExist(sid);
//    //}
//    //return null;
//  }
}
