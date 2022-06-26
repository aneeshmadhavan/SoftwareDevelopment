package CS601.project3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class ChatApplicationTest {
    @BeforeAll
    static void setup() throws InterruptedException {
        new Thread(() -> {ChatApplication.main(null);}).start();
        Thread.sleep(5000);
    }

    @Test
    public void testGet() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/slackbot"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
        assertTrue(response.body().contains("<form"));
    }

    @Test
    public void testPostSlackMessage() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/slackbot"))
                .POST(HttpRequest.BodyPublishers.ofString("message=test message"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
        assertTrue(response.body().contains("Message Sent!"));
    }

    @Test
    public void testIncorrectBodyKey() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/slackbot"))
                .POST(HttpRequest.BodyPublishers.ofString("badmessage=test message 2"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
    }

    @Test
    public void testIncorrectPath() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/slackbot1"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
    }

    @Test
    public void testMethodNotAllowed() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/slackbot"))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(405, response.statusCode());
    }

    @Test
    public void testShutdown() throws InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/shutdown"))
                .GET()
                .timeout(Duration.ofSeconds(1))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(HtmlValidator.isValid(response.body()));
        } catch (IOException e) {
            fail();
        }
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            fail();
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("request timed out"));
        }
    }
}
