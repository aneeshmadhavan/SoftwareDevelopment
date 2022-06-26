package CS601.project3;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ParseRequestTest {

    File tmpFile = File.createTempFile("tmp", null);

    public ParseRequestTest() throws IOException {
    }

    @Test
    public void testGetRequestHandlerValidRequestLine() {
        ParseRequest parseRequest = new ParseRequest();
        try {
            RequestHandler requestHandler = parseRequest.getRequestHandler(null, getWriter(), "GET /reviewsearch HTTP/1.1");
            assertEquals("GET", requestHandler.getMethod());
            assertEquals("/reviewsearch", requestHandler.getPath());
            assertEquals("HTTP/1.1", requestHandler.getVersion());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testGetRequestHandlerBadRequestLine() {
        ParseRequest parseRequest = new ParseRequest();
        try {
            PrintWriter writer = getWriter();
            RequestHandler requestHandler = parseRequest.getRequestHandler(null, writer, "GET /reviewsearch");
            assertNull(requestHandler);
            writer.close();
            assertTrue(new String(Files.readAllBytes(Path.of(tmpFile.getAbsolutePath()))).contains("HTTP/1.1 404 Not Found"));
        } catch (IOException e) {
            fail();
        }
    }

    private PrintWriter getWriter() throws FileNotFoundException {
        OutputStream os = new FileOutputStream(tmpFile);
        return new PrintWriter(os);
    }

}
