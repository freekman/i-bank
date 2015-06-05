package com.clouway.core;

import com.clouway.http.HttpModule;
import com.clouway.http.PageModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.io.FileInputStream;
import java.util.EnumSet;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class Main {
  public static void main(String... args) throws Exception {
    FileInputStream inputStream = null;
    if (args.length > 0) {
      inputStream = new FileInputStream(args[0]);
    }
    final PropertyReader reader = new PropertyReader(inputStream);


    Server server = new Server(reader.getIntProperty("jetty.port"));
    ServletContextHandler handler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
    handler.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    handler.addEventListener(new GuiceServletContextListener() {
                               @Override
                               protected Injector getInjector() {
                                 return Guice.createInjector(new HttpModule(reader), new PageModule());
                               }
                             }
    );
    handler.setResourceBase("src/main/webapp");
    handler.addServlet(DefaultServlet.class, "/");

    server.setHandler(handler);
    server.start();
  }
}

