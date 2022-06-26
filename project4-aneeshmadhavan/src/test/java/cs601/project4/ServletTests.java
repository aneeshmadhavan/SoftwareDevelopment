package cs601.project4;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServletTests {
    static  Thread t;


    @BeforeAll
    static void setup() throws InterruptedException {
        t = new Thread(() -> {
            try {
                new JettyServer().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        Thread.sleep(5000);
    }

    @Test
    public void testHomePageServletDoGet() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://a51c-136-24-18-24.ngrok.io"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void testCreateEventServletDoGet() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://a51c-136-24-18-24.ngrok.io/event/create"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(302, response.statusCode());
    }
    @Test
    public void testSearchEventServletDoGet() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://a51c-136-24-18-24.ngrok.io/event/search"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(302, response.statusCode());
    }
    @Test
    public void testSearchEventServletDoPut() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://a51c-136-24-18-24.ngrok.io/event/search"))
                .PUT(HttpRequest.BodyPublishers.ofString("term=search"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(405, response.statusCode());
    }

    @Test
    public void testSearchEventServletDoPost() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://a51c-136-24-18-24.ngrok.io/event/search"))
                .POST(HttpRequest.BodyPublishers.ofString("term=search"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(302, response.statusCode());
    }

    @AfterAll
    static void shutdown(){
        t.interrupt();
    }



}
