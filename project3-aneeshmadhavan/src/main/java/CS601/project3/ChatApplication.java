package CS601.project3;

import java.io.FileNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that contains the Chat Application.
 *
 * @author Aneesh Madhavan
 */
public class ChatApplication {
    private static final Logger LOGGER = LogManager.getLogger(ChatApplication.class);

    /**
     * Main method for Chat Application.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            ConfigParser.parseConfig("config.json");
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        HttpServer server = new HttpServer(9090);
        server.addMapping("/slackbot", new ChatHandler());
        server.addMapping("/shutdown", new ShutdownHandler(server));
        server.startServer();
    }
}
