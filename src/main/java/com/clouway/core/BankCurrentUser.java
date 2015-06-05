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
  public Optional<User> get() {
    Optional<String> sid = provider.get();
    if (sid.isPresent()) {
      return Optional.of(finder.findBySidIfExist(sid.get()).get());
    }
    return Optional.absent();
  }
}
