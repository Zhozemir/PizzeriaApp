package com.example.pizzeria.console.controller;

import com.example.pizzeria.console.exceptions.ConsoleServerException;
import com.example.pizzeria.console.http.ApiResponse;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.utils.ErrorResponseUtil;
import com.example.pizzeria.controllers.requests.OrderCreateRequest;
import com.example.pizzeria.dto.OrderDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderController {

    private final HttpClientService httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public OrderController(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    public void createOrder(List<Long> productIds){

        ApiResponse response = httpClient.postJson("/orders", new OrderCreateRequest(productIds));

        if(!response.isSuccess()){
            throw new ConsoleServerException("Грешка при създаване на поръчка: " + ErrorResponseUtil.extract(response));
        }

    }

    public List<OrderDTO> getMyOrders() {

        var user = ConsoleSession.getCurrentUser();

        if (user == null || user.getId() == null) {
            throw new ConsoleServerException("Потребителят не е в системата.");
        }

        ApiResponse response = httpClient.get("/orders/my/json?userId=" + user.getId());

        if (!response.isSuccess()) {
            throw new ConsoleServerException("Грешка при зареждане на поръчките: " + ErrorResponseUtil.extract(response));
        }

        try {
            return mapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при парсване на поръчките: " + e.getMessage(), e);
        }

    }

    public void repeatOrder(long orderId) {

        ApiResponse response = httpClient.post("/orders/" + orderId + "/repeat");

        if(!response.isSuccess()){
            throw new ConsoleServerException("Грешка при повторение на поръчка: " + ErrorResponseUtil.extract(response));
        }

    }

    public void updateOrderStatus(long id, String status) {

        ApiResponse response = httpClient.put("/orders/" + id + "/status?status=" + status);

        if (!response.isSuccess()) {
            throw new ConsoleServerException("Грешка при обновяване на статус: " + ErrorResponseUtil.extract(response));
        }

    }

    public List<OrderDTO> getAllOrders() {

        ApiResponse response = httpClient.get("/orders");

        if (!response.isSuccess()) {
            throw new ConsoleServerException("Грешка при зареждане на всички поръчки: " + ErrorResponseUtil.extract(response));
        }

        try {
            return mapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при парсване на поръчките: " + e.getMessage());
        }

    }

    public List<OrderDTO> getMyDeliveredOrders() {

        var user = ConsoleSession.getCurrentUser();

        if (user == null || user.getId() == null) {
            throw new ConsoleServerException("Потребителят не е в системата.");
        }

        ApiResponse response = httpClient.get("/orders/my/json?userId=" + user.getId());

        if (!response.isSuccess()) {
            throw new ConsoleServerException("Грешка при зареждане на моите поръчки: " + ErrorResponseUtil.extract(response));
        }

        try {

            List<OrderDTO> orders = mapper.readValue(response.getBody(), new TypeReference<>() {});
            return orders.stream()
                    .filter(o -> "DELIVERED".equals(o.getStatus()))
                    .toList();

        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при филтриране на поръчки: " + e.getMessage());
        }
    }

    public List<OrderDTO> getOrdersByPeriod(String from, String to) {

        ApiResponse response = httpClient.get("/orders/period?startTime=" + from + "&endTime=" + to);

        if (!response.isSuccess()) {
            throw new ConsoleServerException("Грешка при зареждане на поръчки по период: " + ErrorResponseUtil.extract(response));
        }

        try {
            return mapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при парсване на поръчки: " + e.getMessage(), e);
        }

    }

    public static List<Long> parseIds(String input) {

        try {

            return Arrays.stream(input.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

        } catch (NumberFormatException ex){
            throw new ConsoleServerException("Невалидни ID-та " + input, ex);
        }
    }

    public List<OrderDTO> getMyDeliveredAfter(LocalDateTime since) {

        String param = since.toString();

        ApiResponse response;

        try {
            response = httpClient.get("/orders/my/delivered/after?since=" + param);
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при свързване със сървъра: " + e.getMessage(), e);
        }

        if (!response.isSuccess()) {
            throw new ConsoleServerException("Грешка при зареждане на нови доставени поръчки: " + ErrorResponseUtil.extract(response));
        }

        try {
            return mapper.readValue(response.getBody(), new TypeReference<List<OrderDTO>>() {});
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при парсване на нови доставени поръчки: " + e.getMessage(), e);
        }

    }

}
