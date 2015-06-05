package com.clouway.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */

public interface UserAuthenticator {

  boolean authenticate(User user);
  //If user does not have sid a new sid will bee assigned to him
  void registerSession(User user);
}
