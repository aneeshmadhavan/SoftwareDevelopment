package CS601.project3;

import java.io.IOException;
import static CS601.project3.HandlerUtils.check405;
import static CS601.project3.HttpConstants.SHUTDOWN_PAGE;
import static CS601.project3.ServerUtils.send200;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that implements Shutdown.
 *
 * @author Aneesh Madhavan
 */
public class ShutdownHandler implements Handler {
    HttpServer server;

    /**
     * Constructor for class.
     *
     * @param server
     */
    public ShutdownHandler(HttpServer server) {
        this.server = server;
    }

    /**
     * Handles the shutdown request.
     *
     * @param requestHandler
     * @param responseHandler
     * @throws IOException
     */
    @Override
    public void handle(RequestHandler requestHandler, ResponseHandler responseHandler) throws IOException {
        check405(requestHandler, responseHandler);
        server.shutdown();
        send200(responseHandler.getWriter());
        responseHandler.getWriter().println(SHUTDOWN_PAGE);
    }
}
