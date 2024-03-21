package com.example.DeliveryFeeApplication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.InputStream;

@Service
public class HttpClientService {
    private final HttpClient client;

    public HttpClientService() {
        this.client = HttpClient.newHttpClient();
    }

    public HttpResponse<InputStream> fetchData(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofInputStream());
    }
}
