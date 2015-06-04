package com.clouway.validator;

import com.clouway.core.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class UserFormValidator implements Validator<User> {

  private List<String> list = new ArrayList<>();

  public List<String> validate(User user) {
    if (!user.getName().matches("[a-zA-z0-9]{2,20}")) {
      list.add("The name size must be from 2-20 only letters and numbers!");
    }
    if (!user.getPassword().matches("\\^S|[a-zA-z0-9@!.?-]{2,20}")) {
      list.add("The password size must be from 2-20,without any white space!");
    }
    return list;
  }

}
