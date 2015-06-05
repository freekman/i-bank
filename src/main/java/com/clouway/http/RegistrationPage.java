package com.clouway.http;

import com.clouway.core.ExistingUserException;
import com.clouway.core.Session;
import com.clouway.core.User;
import com.clouway.core.UserRegister;
import com.clouway.validator.Validator;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/register")
@Show("register.html")
public class RegistrationPage {

  public String userName = "";
  public String password = "";
  private List<String> messages;
  private UserRegister userRegistry;
  private Validator<User> validator;

  public List<String> getMessages() {
    return messages;
  }

  @Inject
  public RegistrationPage(UserRegister userRegistry, Validator validator) {
    this.userRegistry = userRegistry;
    this.validator = validator;
  }

  @Post
  public void register() {
    UserDTO userDTO = new UserDTO(userName, password, 0.0);
    User user = new User(userDTO.name, userDTO.password, userDTO.amount, new Session("", userDTO.name, 0));
    messages = validator.validate(user);
    if (messages.isEmpty()) {
      messages= Lists.newArrayList("Registration successful.");
      try {
        userRegistry.register(user);
      } catch (ExistingUserException e) {
        messages= Lists.newArrayList("User already exists.");
      }
    }
  }

}
