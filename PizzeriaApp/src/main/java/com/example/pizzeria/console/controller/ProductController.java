package com.example.pizzeria.console.controller;

import com.example.pizzeria.console.http.ApiResponse;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.utils.ErrorResponseUtil;
import com.example.pizzeria.console.validations.IdValidation;
import com.example.pizzeria.console.validations.ProductPriceValidation;
import com.example.pizzeria.controllers.requests.ProductCreateRequest;
import com.example.pizzeria.dto.ProductDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ProductController {

    private final HttpClientService httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public ProductController(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    public List<ProductDTO> listActive() {

        ApiResponse response = httpClient.get("/products");

        if (!response.isSuccess()) {

            System.err.println("Неуспешно зареждане на продукти: " + ErrorResponseUtil.extract(response));
            return List.of();

        }

        try {

            return mapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {

            System.err.println("Грешка при парсване на продукти: " + e.getMessage());
            return List.of();

        }
    }

    public String add(Scanner scanner) {

        System.out.print("Име: ");
        String name = scanner.nextLine();

        BigDecimal price = BigDecimal.valueOf(ProductPriceValidation.readProductPrice("Цена: "));

        ProductCreateRequest request = new ProductCreateRequest(name, price);
        ApiResponse response = httpClient.postJson("/products/add", request);
        return handleTextResponse(response);
    }

    public String deactivate(Scanner scanner) {

        long id = IdValidation.readId("ID за деактивиране: ");
        ApiResponse response = httpClient.put("/products/" + id + "/deactivate");
        return handleTextResponse(response);

    }

    private String handleTextResponse(ApiResponse response) {

        return response.isSuccess()
                ? response.getBody()
                : ErrorResponseUtil.extract(response);

    }
}