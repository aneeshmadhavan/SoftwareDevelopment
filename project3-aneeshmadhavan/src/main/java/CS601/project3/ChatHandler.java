package CS601.project3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static CS601.project3.HandlerUtils.check405;
import static CS601.project3.HandlerUtils.handleChatPost;
import static CS601.project3.HandlerUtils.handleGet;
import static CS601.project3.HttpConstants.CHAT_PAGE;
import static CS601.project3.HttpConstants.REPLY_FOOTER;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that contains the Chat Handler.
 *
 * @author Aneesh Madhavan
 */
public class ChatHandler implements Handler {

    private static final Logger LOGGER = LogManager.getLogger(ChatHandler.class);
    public static final String MESSAGE = "message";
    public static final String JSON_BODY = "{\n" +
            "\t\"channel\": \"%s\",\n" +
            "\t\"text\": \"%s\"\n" +
            "}";

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
        handleGet(requestHandler, responseHandler, CHAT_PAGE + REPLY_FOOTER);
        handleChatPost(requestHandler, responseHandler);
    }

}
