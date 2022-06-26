package CS601.project3;

import java.io.PrintWriter;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * A utility class to send server response messages.
 */
public class ServerUtils {

    /**
     * Private constructor for Utility class
     */
    private ServerUtils() {
    }

    /**
     * Send the status line of an HTTP 200 OK response.
     * @param writer
     */
    public static void send200(PrintWriter writer) {
        writer.printf("%s %s\r\n", HttpConstants.VERSION, HttpConstants.OK);
        writer.printf("%s \r\n\r\n", HttpConstants.CONNECTION_CLOSE);
    }

    /**
     * Sends the status line of an HTTP 400 Bad Request response
     * @param writer
     */
    public static void send400(PrintWriter writer) {
        writer.printf("%s %s\r\n", HttpConstants.VERSION, HttpConstants.BAD_REQUEST);
        writer.printf("%s \r\n\r\n", HttpConstants.CONNECTION_CLOSE);
        writer.println(HttpConstants.BAD_REQUEST_PAGE);

    }

    /**
     * Send the status line of an HTTP 404 Not Found response.
     * @param writer
     */
    public static void send404(PrintWriter writer) {
        writer.printf("%s %s\r\n", HttpConstants.VERSION, HttpConstants.NOT_FOUND);
        writer.printf("%s \r\n\r\n", HttpConstants.CONNECTION_CLOSE);
        writer.println(HttpConstants.NOT_FOUND_PAGE);
    }

    /**
     * Send the status line of an HTTP 405 Method Not Allowed response.
     * @param writer
     */
    public static void send405(PrintWriter writer) {
        writer.printf("%s %s\r\n", HttpConstants.VERSION, HttpConstants.NOT_ALLOWED);
        writer.printf("%s \r\n\r\n", HttpConstants.CONNECTION_CLOSE);
    }

}