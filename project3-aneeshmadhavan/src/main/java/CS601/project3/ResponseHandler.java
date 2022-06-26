package CS601.project3;

import java.io.PrintWriter;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that wraps the response fields for the handler.
 *
 * @author Aneesh Madhavan
 */
public class ResponseHandler {

    private final PrintWriter writer;

    /**
     * Constructor for the class.
     * @param writer
     */
    public ResponseHandler(PrintWriter writer) {
        this.writer = writer;
    }

    /**
     * Getter for writer.
     *
     * @return
     */
    public PrintWriter getWriter() {
        return writer;
    }
}
