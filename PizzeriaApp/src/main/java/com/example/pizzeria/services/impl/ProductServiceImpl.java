package com.example.PizzeriaApp.services.impl;

import com.example.PizzeriaApp.models.Product;
import com.example.PizzeriaApp.repositories.interfaces.ProductDAO;
import com.example.PizzeriaApp.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    @Override
    public boolean addProduct(String name, BigDecimal price) {

        Product product = new Product(name, price);
        return productDAO.save(product);

    }

    @Override
    public List<Product> getActiveProducts() {
        return productDAO.findActiveProducts();
    }

    @Override
    public boolean deactivateProduct(Long productId) {

        Optional<Product> productOpt = productDAO.findById(productId);

        if(productOpt.isPresent()){

            Product product = productOpt.get();
            product.setActive(false);
            return productDAO.update(product);

        }

        return false;
    }
}
