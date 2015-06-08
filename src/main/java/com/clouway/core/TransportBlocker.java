package com.clouway.core;

import com.google.sitebricks.headless.Reply;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class TransportBlocker implements MethodInterceptor {
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    try {
      return invocation.proceed();
    } catch (HibernateValidationException | ObjectCastException e) {
      return Reply.with("Bad Request").status(400);
    }
  }
}
