package lv.rvt;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RandomWordGenerator {
    private static final String API_URL = "https://random-word-api.vercel.app/api?words=1&length=5";
    private static final HttpClient client = HttpClient.newHttpClient();

    public String getWord() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new IOException("API request failed with status: " + response.statusCode());
        }

        return response.body()
                .replace("[", "")
                .replace("]", "")
                .replace("\"", "")
                .trim();
    }
}