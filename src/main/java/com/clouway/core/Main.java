package com.clouway.core;

import com.clouway.core.PropertyReader;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.FileInputStream;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class Main {

  public static FileInputStream prop;

  public static void main(String[] args) throws Exception {
    if (args.length > 0) {
      System.out.println(prop);
      prop = new FileInputStream(args[0]);
    }

    PropertyReader reader = new PropertyReader();
    Server server = new Server(reader.getIntProperty("jetty.port"));
    WebAppContext webapp = new WebAppContext();
//    webapp.setWar("src/main/webapp");
    webapp.setWar("AngularBank.war");
    server.setHandler(webapp);
    server.start();
    server.join();
  }
}
