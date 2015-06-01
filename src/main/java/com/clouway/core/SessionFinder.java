package com.clouway.core;

import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface SessionFinder {

  List<Session> findAll();

  Session findBySid(String sid);
}
