package com.clouway.core;

import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface UserFinder {

  List<User> findAll();

  User findByName(String name);

  User findBySidIfExist(String sid);
}
