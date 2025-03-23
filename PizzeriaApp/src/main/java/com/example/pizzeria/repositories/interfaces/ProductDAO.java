package com.example.pizzeria.repositories.interfaces;

import com.example.pizzeria.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    boolean save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findActiveProducts();
    boolean update(Product product);
    Optional<Product> findActiveById(Long id);

}

