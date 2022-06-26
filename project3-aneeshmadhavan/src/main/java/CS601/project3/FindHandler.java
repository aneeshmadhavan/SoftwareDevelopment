package CS601.project3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static CS601.project3.HandlerUtils.check405;
import static CS601.project3.HandlerUtils.handleFindPost;
import static CS601.project3.HandlerUtils.handleGet;
import static CS601.project3.HttpConstants.FIND_PAGE;
import static CS601.project3.HttpConstants.REPLY_FOOTER;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that contains the Find Handler.
 *
 * @author Aneesh Madhavan
 */
public class FindHandler implements Handler {

    public static final String ASIN = "asin";
    private static final Logger LOGGER = LogManager.getLogger(FindHandler.class);


    /**
     * Method that handles incoming requests.
     *
     * @param requestHandler
     * @param responseHandler
     * @throws IOException
     */
    @Override
    public void handle(RequestHandler requestHandler, ResponseHandler responseHandler) throws IOException {
        check405(requestHandler, responseHandler);
        handleGet(requestHandler, responseHandler, FIND_PAGE + REPLY_FOOTER);
        handleFindPost(requestHandler, responseHandler);
    }
}
