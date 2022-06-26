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
public class SearchApplicationTest {

    @BeforeAll
    static void setup() throws InterruptedException {
        new Thread(() -> {SearchApplication.main(null);}).start();
        Thread.sleep(5000);
    }

    @Test
    public void testReviewSearchGet() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/reviewsearch"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
        assertTrue(response.body().contains("<form"));
    }

    @Test
    public void testReviewSearchValidPostArchaic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/reviewsearch"))
                .POST(HttpRequest.BodyPublishers.ofString("query=archaic"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
        assertTrue(response.body().contains("archaic"));
    }

    @Test
    public void testReviewSearchIncorrectBodyValue() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/reviewsearch"))
                .POST(HttpRequest.BodyPublishers.ofString("query=hello world"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
        assertTrue(response.body().contains("Bad Request"));
    }

    @Test
    public void testReviewSearchIncorrectBodyKey() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/reviewsearch"))
                .POST(HttpRequest.BodyPublishers.ofString("badQuery=hello"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
    }

    @Test
    public void testReviewSearchIncorrectPath() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/reviewsearch1"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
    }

    @Test
    public void testReviewSearchMethodNotAllowed() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/reviewsearch"))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(405, response.statusCode());
    }

    @Test
    public void testFindGet() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/find"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
        assertTrue(response.body().contains("<form"));
    }

    @Test
    public void testFindValidPostAsin() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/find"))
                .POST(HttpRequest.BodyPublishers.ofString("asin=1466736038"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
        assertTrue(response.body().contains("1466736038"));
    }

    @Test
    public void testFindIncorrectBodyValue() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/find"))
                .POST(HttpRequest.BodyPublishers.ofString("asin=14667 36038"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
        assertTrue(response.body().contains("Bad Request"));
    }

    @Test
    public void testFindIncorrectBodyKey() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/find"))
                .POST(HttpRequest.BodyPublishers.ofString("badasin=1466736038"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
    }

    @Test
    public void testFindIncorrectPath() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/find1"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
        assertTrue(HtmlValidator.isValid(response.body()));
    }

    @Test
    public void testFindMethodNotAllowed() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/find"))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(405, response.statusCode());
    }

    @Test
    public void testShutdown() throws InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/shutdown"))
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
