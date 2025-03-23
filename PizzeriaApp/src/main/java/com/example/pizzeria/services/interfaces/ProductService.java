package com.example.pizzeria.services.interfaces;

import com.example.pizzeria.models.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    boolean addProduct(String name, BigDecimal price);
    List<Product> getActiveProducts();
    boolean deactivateProduct(Long productId);

}
