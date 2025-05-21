package com.example.pizzeria.console.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientService {

    public static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ApiResponse get(String endpoint) {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new ApiResponse(response.statusCode(), response.body());

        } catch (IOException | InterruptedException e) {
            return new ApiResponse(500, "Грешка при връзка със сървъра: " + e.getMessage());
        }
    }

    public ApiResponse post(String endpoint) {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new ApiResponse(response.statusCode(), response.body());

        } catch (IOException | InterruptedException e) {
            return new ApiResponse(500, "Грешка при заявка: " + e.getMessage());
        }
    }


    public ApiResponse postJson(String endpoint, Object body) {

        try {

            String json = objectMapper.writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new ApiResponse(response.statusCode(), response.body());

        } catch (IOException | InterruptedException e) {
            return new ApiResponse(500, "Грешка при връзка със сървъра: " + e.getMessage());
        }
    }

    public ApiResponse put(String endpoint) {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + endpoint))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new ApiResponse(response.statusCode(), response.body());

        } catch (IOException | InterruptedException e) {
            return new ApiResponse(500, "Грешка при връзка със сървъра: " + e.getMessage());
        }
    }

//    private static String sendRequest(HttpRequest request) {
//
//        try {
//
//            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//            return response.body();
//
//        } catch (IOException | InterruptedException e) {
//
//            e.printStackTrace();
//            return "Грешка при заявка!";
//
//        }
//    }

}