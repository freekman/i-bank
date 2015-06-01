package com.clouway.http;

import com.clouway.core.Session;
import com.clouway.core.User;
import com.clouway.core.UserAlreadyExistsException;
import com.clouway.core.UserRegister;
import com.clouway.validator.Validator;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/register")
@Show("register.html")
public class RegistrationPage {

  public String uName = "";
  public String pwd = "";
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
  public void get() {
    UserDTO userDTO = new UserDTO(uName, pwd, 0.0);
    User user = new User(userDTO.getName(), userDTO.getPassword(), userDTO.getAmount(), new Session("", userDTO.getName(), 0));
    messages = validator.validate(user);
    if (messages.size() == 0) {
      messages = new ArrayList<String>() {{
        add("Registration successful.");
      }};
      try {
        userRegistry.register(user);
      } catch (UserAlreadyExistsException e) {
        messages = new ArrayList<String>() {{
          add("User already exists.");
        }};
      }
    }
  }

}
