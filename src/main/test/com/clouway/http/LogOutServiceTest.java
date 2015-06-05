package com.clouway.http;

import com.clouway.core.SessionRegister;

import com.clouway.core.SidProvider;
import com.google.common.base.Optional;
import com.google.inject.Provider;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


import static com.clouway.matchers.ReplyRedirectsTo.redirectsTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LogOutServiceTest {

  private LogOutService logOutService;
  private SessionRegister sessionRegister;
  private SidProvider sidProvider;
  private Provider<HttpServletResponse> resp;
  private HttpServletResponse httpServletResponse;
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    httpServletResponse = context.mock(HttpServletResponse.class);
    sidProvider = context.mock(SidProvider.class);
    resp = context.mock(Provider.class);
    sessionRegister = context.mock(SessionRegister.class);
    logOutService = new LogOutService(sessionRegister, sidProvider, resp);
  }

  @Test
  public void happyPath() throws Exception {
    context.checking(new Expectations() {{
      oneOf(sidProvider).get();
      will(returnValue(Optional.of("asd")));
      oneOf(sessionRegister).clear("asd");
      oneOf(resp).get();
      will(returnValue(httpServletResponse));
      oneOf(httpServletResponse).addCookie(with(any(Cookie.class)));
    }});
    Reply<?> replay = logOutService.get();
    assertThat(replay, is(redirectsTo("/home")));
  }

}