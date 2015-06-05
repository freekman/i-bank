package com.clouway.core;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
public class CurrentSidProvider implements SidProvider {
  private HttpServletRequest req;
  @Inject
  public CurrentSidProvider(HttpServletRequest req) {
    this.req = req;
  }

  @Override
  public Optional<String> get() {
    Cookie[] cookies = req.getCookies();
    if (cookies == null) {
      return Optional.absent();
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equalsIgnoreCase("sid")) {
        return Optional.of(cookie.getValue());
      }
    }
    return Optional.absent();
  }

}
