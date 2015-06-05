package com.clouway.http;

import com.clouway.core.UserAuthenticator;
import com.clouway.core.Session;
import com.clouway.core.User;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/login")
@Show("login.html")
public class LoginPage {
  public String userName = "";
  public String password = "";
  private UserAuthenticator userUserAuthenticator;

  @Inject
  public LoginPage(UserAuthenticator userUserAuthenticator) {
    this.userUserAuthenticator = userUserAuthenticator;
  }

  @Post
  public String login() {
    User user = new User(userName, password, 0.0, new Session("", "", 0));
    if (userUserAuthenticator.authenticate(user)) {
      userUserAuthenticator.registerSession(user);
      return "index.html#/welcome";
    }
    return "/home";
  }
}
