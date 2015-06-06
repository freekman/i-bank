package com.clouway.core;

import com.google.common.base.Optional;

import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface SessionFinder {

  Optional<List<Session>> findAll();

  Optional<Session> findBySid(String sid);
}
