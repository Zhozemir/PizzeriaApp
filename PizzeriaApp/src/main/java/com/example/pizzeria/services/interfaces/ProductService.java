package com.example.pizzeria.services.interfaces;

import com.example.pizzeria.models.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Product addProduct (String name, BigDecimal price);
    List<Product> getActiveProducts();
    void deactivateProduct(Long productId);
}
