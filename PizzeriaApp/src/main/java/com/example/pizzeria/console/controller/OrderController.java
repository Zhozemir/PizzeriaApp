package com.example.pizzeria.console.controller;

import com.example.pizzeria.console.http.ApiResponse;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.utils.ErrorResponseUtil;
import com.example.pizzeria.console.validations.IdValidation;
import com.example.pizzeria.controllers.requests.OrderCreateRequest;
import com.example.pizzeria.dto.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderController {

    private final HttpClientService httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public OrderController(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    public String create(List<Long> productIds) {

        ApiResponse response = httpClient.postJson("/orders/create", new OrderCreateRequest(productIds));
        return handleTextResponse(response);

    }

//    public List<OrderDTO> listMy() {
//
//        var user = ConsoleSession.getCurrentUser();
//        if (user == null || user.getId() == null) return List.of();
//
//        ApiResponse response = httpClient.get("/orders/my/json?userId=" + user.getId());
//
//        if (!response.isSuccess()) {
//
//            System.err.println("Неуспешно зареждане на поръчки: " + ErrorResponseUtil.extract(response));
//            return List.of();
//
//        }
//
//        try {
//
//            return mapper.readValue(response.getBody(), new TypeReference<>() {});
//        } catch (Exception e) {
//
//            System.err.println("Проблем при парсване на поръчки: " + e.getMessage());
//            return List.of();
//
//        }
//    }

//    public List<OrderDTO> listAll() {

//        ApiResponse response = httpClient.get("/orders");
//        if (!response.isSuccess()) return List.of();
//
//        try {
//            return mapper.readValue(response.getBody(), new TypeReference<>() {});
//        } catch (Exception e) {
//            return List.of();
//        }
//    }

    public List<OrderDTO> listMyJson() {

        var user = ConsoleSession.getCurrentUser();

        if (user == null || user.getId() == null) {
            throw new IllegalStateException("Потребителят не е в системата.");
        }

        ApiResponse response = httpClient.get("/orders/my/json?userId=" + user.getId());

        if (!response.isSuccess()) {
            throw new RuntimeException("Грешка при зареждане на поръчките: " + ErrorResponseUtil.extract(response));
        }

        try {
            return mapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Грешка при парсване на поръчките: " + e.getMessage(), e);
        }
    }

//    public List<OrderDTO> reportByPeriod(String from, String to) {
//        ApiResponse response = httpClient.get("/orders/period?start=" + from + "&end=" + to);
//        if (!response.isSuccess()) return List.of();
//
//        try {
//            return mapper.readValue(response.getBody(), new TypeReference<>() {});
//        } catch (Exception e) {
//            return List.of();
//        }
//    }

    public String repeat(long orderId) {

        ApiResponse response = httpClient.post("/orders/" + orderId + "/repeat");
        return handleTextResponse(response);

    }

    public String updateStatus(Scanner scanner) {

        long id = readId(scanner, "ID на поръчка: ");
        System.out.print("Нов статус (IN_PROGRESS, DELIVERED, CANCELLED): ");
        String status = scanner.nextLine().toUpperCase();

        ApiResponse response = httpClient.put("/orders/" + id + "/status?status=" + status);
        return handleTextResponse(response);

    }

    public String printMy() {

        long userId = ConsoleSession.getCurrentUser().getId();
        ApiResponse response = httpClient.get("/orders/my");
        return handleTextResponse(response);

    }

    public String printAll() {
        return handleTextResponse(httpClient.get("/orders/print"));
    }

    public String printMyDelivered() {
        return handleTextResponse(httpClient.get("/orders/my/delivered/print"));
    }

    public String printPeriod(String from, String to) {

        ApiResponse response = httpClient.get("/orders/period/print?startTime=" + from + "&endTime=" + to);
        return handleTextResponse(response);

    }

    public static List<Long> parseIds(String input) {

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());

    }

    private String handleTextResponse(ApiResponse response) {

        return response.isSuccess()
                ? response.getBody()
                : ErrorResponseUtil.extract(response);

    }

    private long readId(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine();

            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Невалидно ID. Опитайте отново.");
            }
        }
    }
}
