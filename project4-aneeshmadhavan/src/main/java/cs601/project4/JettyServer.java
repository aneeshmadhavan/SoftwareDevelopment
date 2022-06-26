package cs601.project4;

import com.google.gson.Gson;
import cs601.utilities.Config;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.io.FileReader;

/**
 * CS601 Project 4, Ticket Purchase Web Application
 * Class that contains the Jetty Server.
 *
 * @author Aneesh Madhavan
 */
public class JettyServer {
    public static final int PORT = 8080;
    private static final String CONFIG_FILENAME = "config.json";
    private Server server;

    /**
     * Method to start the server.
     *
     * @throws Exception
     */
    void start() throws Exception {

        Gson gson = new Gson();
        Config config = gson.fromJson(new FileReader(CONFIG_FILENAME), Config.class);
        int maxThreads = 100;
        int minThreads = 10;
        int idleTimeout = 120;

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);

        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.setConnectors(new Connector[] { connector });

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setAttribute(ServerConstants.CONFIG_KEY, config);

        contextHandler.addServlet(HomePageServlet.class, "/");
        contextHandler.addServlet(SlackServlet.class, "/slack");
        contextHandler.addServlet(LoginServlet.class, "/login");
        contextHandler.addServlet(LogoutServlet.class, "/logout");
        contextHandler.addServlet(UserPageServlet.class, "/user");
        contextHandler.addServlet(TransactionServlet.class, "/user/transactions");
        contextHandler.addServlet(UserInfoServlet.class, "/userinfo");
        contextHandler.addServlet(UpdateInfoServlet.class, "/userinfo/update");
        contextHandler.addServlet(ViewEventServlet.class, "/event");
        contextHandler.addServlet(CreateEventServlet.class, "/event/create");
        contextHandler.addServlet(SearchEventServlet.class, "/event/search");
        contextHandler.addServlet(TicketPurchaseServlet.class, "/ticket/purchase");
        contextHandler.addServlet(TransferTicketServlet.class, "/ticket/transfer");

        server.setHandler(contextHandler);

        server.start();
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        JettyServer server = new JettyServer();
        try {
            server.start();
        } catch (Exception e) {
            // Jetty throws a generic Exception, we have no choice but to catch it.
            e.printStackTrace();
        }
    }

}