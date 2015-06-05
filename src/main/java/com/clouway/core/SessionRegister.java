package com.clouway.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface SessionRegister {

  void create(String sid, String userName, Long timeOut);

  void clear(String sid);

  boolean refresh(String sid, Long newTime);

}
