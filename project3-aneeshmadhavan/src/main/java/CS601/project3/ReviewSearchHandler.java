package CS601.project3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static CS601.project3.HandlerUtils.check405;
import static CS601.project3.HandlerUtils.handleGet;
import static CS601.project3.HandlerUtils.handleReviewSearchPost;
import static CS601.project3.HttpConstants.REPLY_FOOTER;
import static CS601.project3.HttpConstants.REVIEW_PAGE;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that parses the Review Search Handler.
 *
 * @author Aneesh Madhavan
 */
public class ReviewSearchHandler implements Handler{


    public static final String QUERY = "query";

    private static final Logger LOGGER = LogManager.getLogger(ReviewSearchHandler.class);


    /**
     * Method that Handles the incoming request.
     *
     * @param requestHandler
     * @param responseHandler
     * @throws IOException
     */
    @Override
    public void handle(RequestHandler requestHandler, ResponseHandler responseHandler) throws IOException {
        check405(requestHandler, responseHandler);
        handleGet(requestHandler, responseHandler, REVIEW_PAGE + REPLY_FOOTER);
        handleReviewSearchPost(requestHandler, responseHandler);
    }

}



