package com.example.pizzeria.console.controller;

import com.example.pizzeria.console.exceptions.ConsoleServerException;
import com.example.pizzeria.console.exceptions.ConsoleValidationException;
import com.example.pizzeria.console.http.ApiResponse;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.utils.ErrorResponseUtil;
import com.example.pizzeria.controllers.requests.ProductCreateRequest;
import com.example.pizzeria.dto.ProductDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

public class ProductController {

    private final HttpClientService httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public ProductController(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    public List<ProductDTO> listActive() {

        ApiResponse response;

        try {
            response = httpClient.get("/products");
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при свързване със сървъра за зареждане на продукти: " + e.getMessage(), e);
        }

        if (!response.isSuccess())
            throw new ConsoleServerException("Неуспешно зареждане на продукти: " + ErrorResponseUtil.extract(response));

        try {
            return mapper.readValue(response.getBody(), new TypeReference<List<ProductDTO>>() {});
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при парсване на списък с продукти: " + e.getMessage(), e);
        }

    }

    public void addProduct(String name, BigDecimal price) {

        if (name == null || name.isBlank())
            throw new ConsoleValidationException("Името на продукта не може да е празно.");

        if (price == null || price.signum() <= 0)
            throw new ConsoleValidationException("Цената трябва да е строго положително число.");

        ProductCreateRequest request = new ProductCreateRequest(name, price);

        ApiResponse response;

        try {
            response = httpClient.postJson("/products/add", request);
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при свързване със сървъра за добавяне на продукт: " + e.getMessage(), e);
        }

        if (!response.isSuccess())
            throw new ConsoleServerException("Неуспешно добавяне на продукт: " + ErrorResponseUtil.extract(response));

    }

    public void deactivateProduct (long id) {

        if (id <= 0)
            throw new ConsoleValidationException("ID на продукт трябва да е положително число.");

        ApiResponse response;

        try {
            response = httpClient.put("/products/" + id + "/deactivate");
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при свързване със сървъра за деактивиране на продукт: " + e.getMessage(), e);
        }

        if (!response.isSuccess())
            throw new ConsoleServerException("Неуспешно деактивиране на продукт: " + ErrorResponseUtil.extract(response));

    }

}