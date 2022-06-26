package CS601.project3;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static CS601.project3.FindHandler.ASIN;
import static CS601.project3.HttpConstants.CHAT_PAGE;
import static CS601.project3.HttpConstants.FIND_PAGE;
import static CS601.project3.HttpConstants.REPLY_FOOTER;
import static CS601.project3.HttpConstants.REVIEW_PAGE;
import static CS601.project3.ReviewSearchHandler.QUERY;
import static CS601.project3.SearchApplication.qaList;
import static CS601.project3.SearchApplication.reviewInvertedIndex;
import static CS601.project3.SearchApplication.reviewList;
import static CS601.project3.ServerUtils.send200;
import static CS601.project3.ServerUtils.send400;
import static CS601.project3.ServerUtils.send404;
import static CS601.project3.ServerUtils.send405;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that contains utility methods for the handlers.
 *
 * @author Aneesh Madhavan
 */
public class HandlerUtils {

    private static final Logger LOGGER = LogManager.getLogger(HandlerUtils.class);
    public static final String MESSAGE = "message";
    public static final String JSON_BODY = "{\n" +
            "\t\"channel\": \"%s\",\n" +
            "\t\"text\": \"%s\"\n" +
            "}";



    /**
     * Private constructor for Utility class
     */
    private HandlerUtils() {
    }

    /**
     * Method that extracts the body value.
     *
     * @param requestHandler
     * @param responseHandler
     * @param key
     * @return
     * @throws IOException
     */
    static String extractBodyValue(RequestHandler requestHandler,
                                          ResponseHandler responseHandler,
                                          String key) throws IOException {
        char[] bodyArr = new char[requestHandler.getContentLength()];
        requestHandler.getInstream().read(bodyArr, 0, bodyArr.length);
        String body = new String(bodyArr);
        LOGGER.info("Message body : {}", body);

        String bodyKey = URLDecoder.decode(body.substring(0, body.indexOf("=")),
                StandardCharsets.UTF_8.toString());
        if (!bodyKey.equals(key)) {
            send404(responseHandler.getWriter());
            return null;
        }

        String bodyValue = URLDecoder.decode(body.substring(body.indexOf("=") + 1),
                StandardCharsets.UTF_8.toString());
        LOGGER.info("Message body value : {}", bodyValue);
        return bodyValue;
    }

    /**
     * Method that writes the input list.
     *
     * @param responseHandler
     * @param list
     */
    static void writeList(ResponseHandler responseHandler, List list) {
        for (Object o : list) {
            responseHandler.getWriter().println("<li>" + escapeHtml4(o.toString()) + "</li>\n");
        }
    }

    /**
     * Method that handles a GET request.
     *
     * @param requestHandler
     * @param responseHandler
     * @param html
     */
    static void handleGet(RequestHandler requestHandler, ResponseHandler responseHandler, String html) {
        if (requestHandler.getMethod().equals(HttpConstants.GET)) {
            send200(responseHandler.getWriter());
            responseHandler.getWriter().println(html);
        }
    }

    /**
     * Method that checks for HTTP methods not allowed.
     *
     * @param requestHandler
     * @param responseHandler
     */
    static void check405(RequestHandler requestHandler, ResponseHandler responseHandler) {
        if (!requestHandler.getMethod().equals(HttpConstants.GET) && !requestHandler.getMethod().equals(HttpConstants.POST)) {
            send405(responseHandler.getWriter());
        }
    }


    /**
     * Method that handles post for Chat Handler.
     *
     * @param requestHandler
     * @param responseHandler
     */
    static void handleChatPost(RequestHandler requestHandler, ResponseHandler responseHandler) {
        if (requestHandler.getMethod().equals(HttpConstants.POST)) {
            String bodyValue = null;
            try {
                bodyValue = extractBodyValue(requestHandler, responseHandler, MESSAGE);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            if (bodyValue == null) {
                LOGGER.debug("Empty Body Value");
                return;
            }
            String json = String.format(JSON_BODY, ConfigParser.config.getChannelId(), bodyValue);
            ServerUtils.send200(responseHandler.getWriter());
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://slack.com/api/chat.postMessage"))
                    .header("Authorization", "Bearer " + ConfigParser.config.getSlackToken())
                    .header("Content-Type", "application/json; utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            try {
                HttpResponse<String> response = null;
                try {
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
                if (response.statusCode() == 200) {
                    responseHandler.getWriter().println(CHAT_PAGE);
                    responseHandler.getWriter().println("<h1>" + "Message Sent! :" + "</h1>\n");
                    responseHandler.getWriter().println(REPLY_FOOTER);
                }
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * Method that handles Post for Find Handler.
     *
     * @param requestHandler
     * @param responseHandler
     */
    static void handleFindPost(RequestHandler requestHandler, ResponseHandler responseHandler) {
        if (requestHandler.getMethod().equals(HttpConstants.POST)) {
            String bodyValue = null;
            try {
                bodyValue = extractBodyValue(requestHandler, responseHandler, ASIN);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            if (bodyValue == null) {
                return;
            }
            if (StringUtils.containsWhitespace(bodyValue)) {
                send400(responseHandler.getWriter());
                return;
            }

            ServerUtils.send200(responseHandler.getWriter());
            responseHandler.getWriter().println(FIND_PAGE);
            responseHandler.getWriter().println("<h1>" + "Reviews with Asin :" + "</h1>\n");
            List list = Data.findAsin(reviewList, bodyValue);
            writeList(responseHandler, list);
            responseHandler.getWriter().println("<h1>" + "QA's with Asin :" + "</h1>\n");
            list = Data.findAsin(qaList, bodyValue);
            writeList(responseHandler, list);
            responseHandler.getWriter().println(REPLY_FOOTER);
        }
    }

    /**
     * Method that handles post for Review Search Handler.
     *
     * @param requestHandler
     * @param responseHandler
     */
    static void handleReviewSearchPost(RequestHandler requestHandler, ResponseHandler responseHandler) {
        if (requestHandler.getMethod().equals(HttpConstants.POST)) {
            String bodyValue = null;
            try {
                bodyValue = extractBodyValue(requestHandler, responseHandler, QUERY);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            if (bodyValue == null) {
                return;
            }
            if (StringUtils.containsWhitespace(bodyValue)) {
                send400(responseHandler.getWriter());
                return;
            }
            ServerUtils.send200(responseHandler.getWriter());
            responseHandler.getWriter().println(REVIEW_PAGE);
            responseHandler.getWriter().println("<h1>" + "Reviews with Term :" + "</h1>\n");
            Map<Integer, Integer> reviewMap = reviewInvertedIndex.searchIndex(bodyValue);
            List<Integer> reviewItems = Data.sortMap(reviewMap);
            List list = Data.findData(reviewList, reviewItems);
            writeList(responseHandler, list);
            responseHandler.getWriter().println(REPLY_FOOTER);
        }
    }


}
