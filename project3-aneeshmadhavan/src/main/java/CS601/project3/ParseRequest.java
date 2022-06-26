package CS601.project3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CS601 Project 3, Flexible HTTP Server supporting
 * different Web Applications.
 * Class that parses the request
 * assigned to it by the threadpool.
 *
 * @author Aneesh Madhavan
 */
public class ParseRequest implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ParseRequest.class);
    Socket socket;
    private Map<String, Handler> mappings;

    /**
     * Constructor for the class.
     *
     * @param socket
     * @param mappings
     */
    public ParseRequest(Socket socket, Map<String, Handler> mappings) {
        this.socket = socket;
        this.mappings = mappings;
    }

    /**
     * Default constructor
     */
    public ParseRequest() {
    }

    /**
     * Parses the incoming request.
     */
    @Override
    public void run() {
        try (BufferedReader instream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            String requestLine = instream.readLine();
            LOGGER.info("Request = {}", requestLine);

            RequestHandler requestHandler = getRequestHandler(instream, writer, requestLine);
            if (requestHandler == null) {
                return;
            }
            ResponseHandler responseHandler = new ResponseHandler(writer);
            Handler handlerType = mappings.get(requestHandler.getPath());
            if (handlerType != null) {
                handlerType.handle(requestHandler, responseHandler);
            } else {
                ServerUtils.send404(writer);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }

    /**
     * Method that returns a request handler
     * given an input stream and a request line.
     *
     * @param instream
     * @param writer
     * @param requestLine
     * @return
     * @throws IOException
     */
    RequestHandler getRequestHandler(BufferedReader instream, PrintWriter writer, String requestLine) throws IOException {
        String[] requestLineParts = requestLine.split("\\s");

        if (requestLineParts.length != 3) {
            ServerUtils.send404(writer);
            return null;
        }

        String method = requestLineParts[0];
        String path = requestLineParts[1];
        String version = requestLineParts[2];

        LOGGER.debug("Method : {}", method);
        LOGGER.debug("Path : {}", path);
        LOGGER.debug("Version : {}", version);

        int contentLength = extractHeadersAndGetContentLength(instream);

        return new RequestHandler(method, path, version, contentLength, instream);
    }

    /**
     * Method that extracts headers and
     * returns content length from the request.
     *
     * @param instream
     * @return
     * @throws IOException
     */
    private int extractHeadersAndGetContentLength(BufferedReader instream) throws IOException {
        int contentLength = 0;
        List<String> headers = new ArrayList<>();
        String header;

        if (instream == null) {
            return contentLength;
        }

        while (!(header = instream.readLine()).isEmpty()) {
            headers.add(header);
            if (header.startsWith(HttpConstants.CONTENT_LENGTH)) {
                String[] contentLengthParts = header.split("\\s");
                contentLength = Integer.parseInt(contentLengthParts[1]);
            }
        }
        return contentLength;
    }
}
