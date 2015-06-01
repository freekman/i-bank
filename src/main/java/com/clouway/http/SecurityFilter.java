package com.clouway.http;

import com.clouway.core.Session;
import com.clouway.core.SessionFinder;
import com.clouway.core.SessionRegister;
import com.clouway.core.CurrentSidProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;


/**
 * Created byivan.genchev1989@gmail.com.
 */
@Singleton
public class SecurityFilter implements Filter {

  private SessionFinder sessionFinder;
  private SessionRegister sessionRegister;

  @Inject
  public SecurityFilter(SessionFinder sessionFinder, SessionRegister sessionRegister) {
    this.sessionFinder = sessionFinder;
    this.sessionRegister = sessionRegister;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("Security Filter Created.");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    boolean isActive = false;
    String reqURI = ((HttpServletRequest) request).getRequestURI();
    String destination = getDestination(reqURI);
    String sessionId = new CurrentSidProvider((HttpServletRequest) request).get();
    Session session = sessionFinder.findBySid(sessionId);

    Session currentSession = null;
    if (null != session) {
      currentSession = session;
    }
    if (currentSession != null) {
      isActive = sessionRegister.refreshSession(currentSession.getSessionId(), Calendar.getInstance().getTimeInMillis());
    }
    if (currentSession == null && (destination.equals("home") || destination.equals("login"))) {
      chain.doFilter(request, response);
      return;
    }
    if (!isActive && (destination.equalsIgnoreCase("home") || destination.equalsIgnoreCase("login") || destination.equalsIgnoreCase("register"))) {
      chain.doFilter(request, response);
      return;
    }

    if (isActive && (destination.equalsIgnoreCase("info") || destination.equalsIgnoreCase("bank"))) {
      chain.doFilter(request, response);
      return;
    }
    if (!isActive && currentSession == null) {
      ((HttpServletResponse) response).sendError(401);
    } else if (isActive && (destination.equalsIgnoreCase("home") || destination.equalsIgnoreCase("login") || destination.equalsIgnoreCase("register"))) {
      ((HttpServletResponse) response).sendRedirect("#/welcome");
    }
  }

  @Override
  public void destroy() {

  }

  private String getDestination(String reqURI) {
    String[] destination = reqURI.split("/");
    return destination[destination.length - 1];
  }
}
