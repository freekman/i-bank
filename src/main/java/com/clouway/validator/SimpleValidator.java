package com.clouway.validator;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface SimpleValidator<T> {
  boolean isValid(T t);
}
