package CS601.project3;

import java.io.IOException;
/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Interface for the Handler.
 *
 * @author Aneesh Madhavan
 */
public interface Handler {
    /**
     * Method that handles the incoming requests.
     *
     * @param requestHandler
     * @param responseHandler
     * @throws IOException
     */
    void handle(RequestHandler requestHandler, ResponseHandler responseHandler) throws IOException;
}
