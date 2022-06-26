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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ServerUtilsTest {

    File tmpFile = File.createTempFile("tmp", null);

    public ServerUtilsTest() throws IOException {
    }

    @Test
    public void testSend200() {
        try {
            PrintWriter writer = getWriter();
            ServerUtils.send200(writer);
            writer.close();
            assertTrue(new String(Files.readAllBytes(Path.of(tmpFile.getAbsolutePath()))).contains("HTTP/1.1 200 OK"));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testSend400() {
        try {
            PrintWriter writer = getWriter();
            ServerUtils.send400(writer);
            writer.close();
            assertTrue(new String(Files.readAllBytes(Path.of(tmpFile.getAbsolutePath()))).contains("HTTP/1.1 400 Bad Request"));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testSend404() {
        try {
            PrintWriter writer = getWriter();
            ServerUtils.send404(writer);
            writer.close();
            assertTrue(new String(Files.readAllBytes(Path.of(tmpFile.getAbsolutePath()))).contains("HTTP/1.1 404 Not Found"));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testSend405() {
        try {
            PrintWriter writer = getWriter();
            ServerUtils.send405(writer);
            writer.close();
            assertTrue(new String(Files.readAllBytes(Path.of(tmpFile.getAbsolutePath()))).contains("HTTP/1.1 405 Method Not Allowed"));
        } catch (IOException e) {
            fail();
        }
    }

    private PrintWriter getWriter() throws FileNotFoundException {
        OutputStream os = new FileOutputStream(tmpFile);
        return new PrintWriter(os);
    }
}
