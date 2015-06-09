package com.clouway.core;

import com.google.sitebricks.headless.Reply;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class TransportInterceptor implements MethodInterceptor {

  /**
   * @param invocation
   * @return an invocation to proceed if no error is cached or a replay with status 400
   * @throws Throwable
   */
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    try {
      return invocation.proceed();
    } catch (HibernateValidationException | ObjectCastException e) {
      return Reply.with("Bad Request").status(400);
    }
  }
}
