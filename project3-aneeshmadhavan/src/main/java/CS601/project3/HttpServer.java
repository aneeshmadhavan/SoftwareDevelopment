package CS601.project3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that contains the Http Server.
 *
 * @author Aneesh Madhavan
 */
public class HttpServer {

    private volatile boolean running = false;
    private final int port;
    private static final Map<String, Handler> mappings = new HashMap<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(100);

    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class);

    /**
     * Constructor for the class.
     *
     * @param port
     */
    public HttpServer(int port) {
        this.port = port;
    }

    /**
     * Method that starts the server.
     */
    public void startServer() {
        running = true;
        try (ServerSocket server = new ServerSocket(port)) {
            LOGGER.info("Server Listening on port : {}", port);

            while (running) {
                LOGGER.info("Waiting for client connection");
                Socket socket = server.accept();
                LOGGER.info("New Connection from : {}", socket.getInetAddress());
                if (!threadPool.isTerminated()) {
                    threadPool.execute(new ParseRequest(socket, mappings));
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Method that maps a path to a handler.
     *
     * @param path
     * @param handler
     */
    public void addMapping(String path, Handler handler) {
        mappings.put(path, handler);
    }

    /**
     * Method that shuts down the server.
     */
    public void shutdown() {
        running = false;
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(0, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
