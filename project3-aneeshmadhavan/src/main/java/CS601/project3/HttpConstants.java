package CS601.project3;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * A helper class to store various constants used for the HTTP server.
 */
public class HttpConstants {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String VERSION = "HTTP/1.1";

    public static final String OK = "200 OK";
    public static final String BAD_REQUEST = "400 Bad Request";
    public static final String NOT_FOUND = "404 Not Found";
    public static final String NOT_ALLOWED = "405 Method Not Allowed";

    public static final String CONTENT_LENGTH = "Content-Length:";
    public static final String CONNECTION_CLOSE = "Connection: close";

    public static final String NOT_FOUND_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Resource not found</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>The resource you are looking for was not found.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static final String BAD_REQUEST_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Bad Request</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>Bad Request, please enter 1 search term</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static final String SHUTDOWN_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Shutdown</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>Server Shutting down</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static final String FIND_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<body>\n" +
            "\n" +
            "<h1>Enter Asin value : </h1>\n" +
            "\n" +
            "<form action=\"/find\" method=\"post\">\n" +
            "  <label for=\"fname\">Search:</label>\n" +
            "  <input type=\"text\" id=\"asin\" name=\"asin\"/><br/><br/>\n" +
            "  <button type=\"submit\">Submit</button>\n" +
            "</form>\n";

    public static final String CHAT_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<body>\n" +
            "\n" +
            "<h1>Enter message : </h1>\n" +
            "\n" +
            "<form action=\"/slackbot\" method=\"post\">\n" +
            "  <label for=\"fname\">Search:</label>\n" +
            "  <input type=\"text\" id=\"message\" name=\"message\"/><br/><br/>\n" +
            "  <button type=\"submit\">Submit</button>\n" +
            "</form>\n";

    public static final String REVIEW_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<body>\n" +
            "\n" +
            "<h1>Enter Review Search Term</h1>\n" +
            "\n" +
            "<form action=\"/reviewsearch\" method=\"post\">\n" +
            "  <label for=\"fname\">Search:</label>\n" +
            "  <input type=\"text\" id=\"query\" name=\"query\"/><br/><br/>\n" +
            "  <button type=\"submit\">Submit </button>\n" +
            "</form>\n";

    public static final String REPLY_FOOTER =
            "\n" +
                    "</body>\n" +
                    "</html>\n";

    /**
     * Constructor for the class.
     */
    private HttpConstants() {
    }

}