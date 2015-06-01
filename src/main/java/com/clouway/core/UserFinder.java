package com.clouway.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface UserFinder {

  User findByName(String name);

  User findBySidIfExist(String sid);
}
