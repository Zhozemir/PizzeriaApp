package com.example.pizzeria.console.controller;

import com.example.pizzeria.console.http.ApiResponse;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.utils.ErrorResponseUtil;
import com.example.pizzeria.console.validations.IdValidation;
import com.example.pizzeria.console.validations.ProductPriceValidation;
import com.example.pizzeria.console.view.input.IdInput;
import com.example.pizzeria.console.view.input.ProductPriceInput;
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
            throw new RuntimeException("Неуспешно зареждане на продукти: " + ErrorResponseUtil.extract(response));
        }

        try {
            return mapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Грешка при парсване на продукти: " + e.getMessage(), e);
        }
    }

    public void addProduct(String name, BigDecimal price) {

        ProductCreateRequest request = new ProductCreateRequest(name, price);
        ApiResponse response = httpClient.postJson("/products/add", request);

        if (!response.isSuccess()) {
            throw new RuntimeException("Грешка при добавяне на продукт: " + ErrorResponseUtil.extract(response));
        }

    }

    public void deactivateProduct(long id) {

        ApiResponse response = httpClient.put("/products/" + id + "/deactivate");

        if (!response.isSuccess()) {
            throw new RuntimeException("Грешка при деактивиране на продукт: " + ErrorResponseUtil.extract(response));
        }

    }

}