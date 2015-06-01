package com.clouway.validator;

import java.util.List;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
public interface Validator<T> {

  List<String> validate(T obj);


}
