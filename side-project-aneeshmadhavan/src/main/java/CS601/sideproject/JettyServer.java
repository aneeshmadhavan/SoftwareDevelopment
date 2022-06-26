package CS601.sideproject;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import utilities.Config;

import java.io.FileReader;

/**
 * CS601 Side Project, Expenses Manager Application
 * Class that contains the Web Server.
 *
 * @author Aneesh Madhavan
 */

public class JettyServer {

    public static final int PORT = 9000;
    private static final String configFilename = "config.json";
    private Server server;

    /**
     * Method that starts the server.
     *
     * @throws Exception
     */
    void start() throws Exception {

        Gson gson = new Gson();
        Config config = gson.fromJson(new FileReader(configFilename), Config.class);
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
        contextHandler.addServlet(LandingServlet.class, "/slack");
        contextHandler.addServlet(LoginServlet.class, "/login");
        contextHandler.addServlet(DataServlet.class, "/data");
        contextHandler.addServlet(LogoutServlet.class, "/logout");
        contextHandler.addServlet(IncomeServlet.class, "/income");
        contextHandler.addServlet(ExpensesServlet.class, "/expenses");
        contextHandler.addServlet(UpdateIncomeServlet.class, "/income/update");
        contextHandler.addServlet(SpendAnalyzerServlet.class, "/analyzer");
        contextHandler.addServlet(HomeServlet.class, "/");

        server.setHandler(contextHandler);

        server.start();

    }

    /**
     * Main method for class.
     *
     * @param args
     */
    public static void main(String[] args) {
        JettyServer server = new JettyServer();
        try {
            server.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}