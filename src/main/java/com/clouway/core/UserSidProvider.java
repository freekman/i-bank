package com.clouway.core;

import com.google.inject.Inject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
public class UserSidProvider implements SidProvider {
  private HttpServletRequest req;

  @Inject
  public UserSidProvider(HttpServletRequest req) {
    this.req = req;
  }

  @Override
  public String get() {
    Cookie[] cookies = req.getCookies();
    if (cookies == null) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equalsIgnoreCase("sid")) {
        return cookie.getValue();
      }
    }
    return null;
  }

  public Cookie getCookie() {

    Cookie[] cookies = req.getCookies();
    if (cookies == null) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equalsIgnoreCase("sid")) {
        return cookie;
      }
    }
    return null;

  }
}
