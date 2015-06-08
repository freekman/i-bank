package com.clouway.http;

import com.clouway.core.SecureTransport;
import com.clouway.core.TransportBlocker;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class InterceptorModule extends AbstractModule {
  @Override
  protected void configure() {
    bindInterceptor(Matchers.annotatedWith(SecureTransport.class),Matchers.any(), new TransportBlocker());
  }
}
