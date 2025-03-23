package com.example.pizzeria.console;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsoleService {

    public static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String get(String endpoint) {
        return sendRequest(HttpRequest.newBuilder().uri(URI.create(BASE_URL + endpoint)).GET().build());
    }

    public static String post(String endpoint) {
        return sendRequest(HttpRequest.newBuilder().uri(URI.create(BASE_URL + endpoint)).POST(HttpRequest.BodyPublishers.noBody()).build());
    }

    public static String postJson(String endpoint, Object requestBody) {

        try {

            String json = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            return sendRequest(request);

        } catch (IOException e) {
            e.printStackTrace();
            return "Грешка при обработката на JSON!";
        }
    }

    public static String put(String endpoint) {
        return sendRequest(HttpRequest.newBuilder().uri(URI.create(BASE_URL + endpoint)).PUT(HttpRequest.BodyPublishers.noBody()).build());
    }

    private static String sendRequest(HttpRequest request) {

        try {

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
            return "Грешка при заявка!";

        }
    }

}