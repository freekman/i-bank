package com.clouway.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface SessionRegister {

  void createSession(String sid, String userName, Long timeOut);

  void clearSession(String sid);

  boolean refreshSession(String sid, Long newTime);

}
