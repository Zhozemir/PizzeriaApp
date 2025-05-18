package com.example.pizzeria.console.controller;

import com.example.pizzeria.console.ConsoleService;
import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.validations.IdValidation;
import com.example.pizzeria.controllers.requests.OrderCreateRequest;
import com.example.pizzeria.dto.OrderDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderController {

    private final ObjectMapper mapper = new ObjectMapper();

    public void create(List<Long> productIds) {

        String response = ConsoleService.postJson(
                "/orders/create",
                new OrderCreateRequest(productIds)
        );

        System.out.println(response);
    }

    public List<OrderDTO> listMy() throws Exception {

        String resp = ConsoleService.get("/orders/my?userId=" + ConsoleSession.getCurrentUser().getId());

        if (resp == null || resp.trim().isEmpty() || resp.trim().startsWith("Няма")) {
            return List.of();
        }

        return mapper.readValue(resp, new TypeReference<List<OrderDTO>>() {});
    }

    public List<OrderDTO> listDelivered() {

        String resp = ConsoleService.get("/orders/delivered/print");

        if (resp == null || resp.trim().isEmpty() || resp.trim().startsWith("Няма")) {
            return List.of();
        }

        try {
            return mapper.readValue(resp, new TypeReference<List<OrderDTO>>() {});
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<OrderDTO> listAll() throws Exception {

        String resp = ConsoleService.get("/orders");

        if (resp == null || resp.trim().isEmpty() || resp.trim().startsWith("Няма")) {
            return List.of();
        }

        return mapper.readValue(resp, new TypeReference<List<OrderDTO>>() {});
    }

    public List<OrderDTO> reportByPeriod(String from, String to) throws Exception {

        String resp = ConsoleService.get("/orders/period?start=" + from + "&end=" + to);

        if (resp == null || resp.trim().isEmpty() || resp.trim().startsWith("Няма")) {
            return List.of();
        }

        return mapper.readValue(resp, new TypeReference<List<OrderDTO>>() {});
    }
    public void repeat(long orderId) {

        String response = ConsoleService.post("/orders/" + orderId + "/repeat");
        System.out.println(response);

    }

    public void updateStatus(Scanner scanner) {

        long id = IdValidation.readId("ID на поръчка: ");
        System.out.print("Нов статус (IN_PROGRESS, DELIVERED, CANCELLED): ");
        String status = scanner.nextLine().toUpperCase();
        String response = ConsoleService.put("/orders/" + id + "/status?status=" + status);
        System.out.println(response);

    }

    public static List<Long> parseIds(String input) {

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());

    }

    public String printMy(){

        long userId = ConsoleSession.getCurrentUser().getId();
        String response = ConsoleService.get("/orders/my");
        return response;

    }

    public String printAll(){
        return ConsoleService.get("/orders/print");
    }

    public String printMyDelivered(){
        return ConsoleService.get("/orders/my/delivered/print");
    }

    public String printPeriod(String from, String to){
        return ConsoleService.get("/orders/period/print?startTime=" + from + "&endTime=" + to);
    }

}
