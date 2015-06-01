package com.clouway.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface Authenticator<T> {

  boolean authenticate(T t);

  void createAndSaveSession(T t);
}
