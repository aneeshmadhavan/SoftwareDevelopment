package CS601.project3;

import java.io.BufferedReader;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that wraps the request fields for the handler.
 *
 * @author Aneesh Madhavan
 */
public class RequestHandler {

    private final String method;
    private final String path;
    private final String version;
    private final int contentLength;
    private final BufferedReader instream;

    /**
     * Constructor for the class.
     *
     * @param method
     * @param path
     * @param version
     * @param contentLength
     * @param instream
     */
    public RequestHandler(String method, String path, String version, int contentLength, BufferedReader instream) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.contentLength = contentLength;
        this.instream = instream;
    }

    /**
     * Getter for method.
     *
     * @return
     */
    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    /**
     * Getter for contentLength.
     *
     * @return
     */
    public int getContentLength() {
        return contentLength;
    }

    /**
     * Getter for instream.
     *
     * @return
     */
    public BufferedReader getInstream() {
        return instream;
    }
}
