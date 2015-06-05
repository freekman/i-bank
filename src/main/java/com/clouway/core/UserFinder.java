package com.clouway.core;

import com.google.common.base.Optional;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface UserFinder {

  Optional<User> findByName(String name);

  Optional<User> findBySidIfExist(String sid);
}
