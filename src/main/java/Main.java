import com.clouway.core.PropertyReader;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class Main {
  public static void main(String[] args) throws Exception {
    PropertyReader reader = new PropertyReader();
    Server server = new Server(reader.getIntProperty("jetty.port"));
    WebAppContext webapp = new WebAppContext();
    webapp.setWar("src/main/webapp/");
    server.setHandler(webapp);
    server.start();
    server.join();
  }
}
