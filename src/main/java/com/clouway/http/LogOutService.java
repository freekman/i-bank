package com.clouway.http;

import com.clouway.core.SessionRegister;
import com.clouway.core.SidProvider;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/logout")
@Service
public class LogOutService {

  private final SessionRegister sessionRegister;
  private SidProvider sidProvider;
  private Provider<HttpServletResponse> resp;


  @Inject
  public LogOutService(SessionRegister sessionRegister, SidProvider sidProvider, Provider<HttpServletResponse> resp) {
    this.sessionRegister = sessionRegister;
    this.sidProvider = sidProvider;
    this.resp = resp;
  }

  @Get
  public Reply<?> get() {
    String sid = sidProvider.get();

    if (null != sid) {
      sessionRegister.clearSession(sid);
    }
    removeCookie();
    return Reply.saying().redirect("/home");
  }

  private void removeCookie() {
    Cookie cookie = new Cookie("sid", null);
    cookie.setMaxAge(0);
    resp.get().addCookie(cookie);
  }
}
