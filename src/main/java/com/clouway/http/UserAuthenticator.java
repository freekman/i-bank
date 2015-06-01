package com.clouway.http;

import com.clouway.core.Authenticator;
import com.clouway.core.SessionRegister;
import com.clouway.core.User;
import com.clouway.core.UserFinder;
import com.clouway.core.CurrentSidProvider;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/authenticator")
@Show("login.html")
public class UserAuthenticator implements Authenticator<User> {

  private final UserFinder userFinder;
  private final SessionRegister sessionRegister;
  private final Provider<HttpServletRequest> requestProvider;
  private final Provider<HttpServletResponse> responseProvider;

  @Inject
  public UserAuthenticator(UserFinder userFinder, SessionRegister sessionRegister, Provider<HttpServletRequest> requestProvider, Provider<HttpServletResponse> responseProvider) {
    this.userFinder = userFinder;
    this.sessionRegister = sessionRegister;
    this.requestProvider = requestProvider;
    this.responseProvider = responseProvider;
  }

  public boolean authenticate(User user) {
    User foundUser = userFinder.findByName(user.getName());
    if ((null != foundUser && foundUser.getName().equals(user.getName())) && foundUser.getPassword().equals(user.getPassword())) {
      return true;
    }
    return false;
  }

  public void createAndSaveSession(User user) {
    String sid = new CurrentSidProvider(requestProvider.get()).get();
    if (sid == null) {
      UUID uuid = new UUID(10, 5);
      String randomValue = "vankaBanka" + uuid.randomUUID().toString();
      sid = sha1(randomValue);
      responseProvider.get().addCookie(new Cookie("sid", sid));
    }
    sessionRegister.createSession(sid, user.getName(), Calendar.getInstance().getTimeInMillis());
  }

  private String sha1(String input) {
    MessageDigest mDigest = null;
    try {
      mDigest = MessageDigest.getInstance("SHA1");
    } catch (NoSuchAlgorithmException e) {
      return "";
    }
    byte[] result = mDigest.digest(input.getBytes());
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < result.length; i++) {
      sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }
}
