package com.example.pizzeria.console.controller;

import com.example.pizzeria.console.ConsoleService;
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

    private final ObjectMapper mapper = new ObjectMapper();

    public List<ProductDTO> listActive() throws Exception {

        String json = ConsoleService.get("/products");
        return mapper.readValue(json, new TypeReference<List<ProductDTO>>(){});

    }

    public void add (Scanner scanner) {

        System.out.print("Име: ");
        String name = scanner.nextLine();

        BigDecimal price = BigDecimal.valueOf(
                ProductPriceValidation.readProductPrice("Цена: ")
        );

        String response = ConsoleService.postJson(
                "/products/add",
                new ProductCreateRequest(name, price)
        );

        System.out.println(response);
    }

    public void deactivate(Scanner scanner) {

        long id = IdValidation.readId("ID за деактивиране: ");
        String response = ConsoleService.put("/products/" + id + "/deactivate");
        System.out.println(response);

    }

}
