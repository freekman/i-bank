package com.clouway.http;

import com.google.sitebricks.SitebricksModule;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class PageModule extends SitebricksModule {
  @Override
  protected void configureSitebricks() {
    at("/info").show(LoginPage.class);
    scan(RegistrationPage.class.getPackage());
  }
}
