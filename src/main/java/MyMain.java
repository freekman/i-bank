import com.clouway.http.HttpModule;
import com.clouway.http.PageModule;
import com.google.inject.Guice;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;

import static java.util.EnumSet.allOf;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class MyMain {
  public static void main(String... args) throws Exception {
    Guice.createInjector(new HttpModule(), new PageModule());

    Server server = new Server(8080);
    ServletContextHandler handler =
            new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
    handler.addFilter(GuiceFilter.class, "/*", allOf(DispatcherType.class));
    handler.setResourceBase("src/main/webapp");
    handler.addServlet(DefaultServlet.class, "/");

    server.setHandler(handler);
    server.start();
  }
}