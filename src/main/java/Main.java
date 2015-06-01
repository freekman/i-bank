import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
/**
 * Created byivan.genchev1989@gmail.com.
 */
public class Main {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    WebAppContext webapp = new WebAppContext();
    webapp.setWar("AngularBank.war");
    server.setHandler(webapp);
    server.start();
    server.join();
  }
}
