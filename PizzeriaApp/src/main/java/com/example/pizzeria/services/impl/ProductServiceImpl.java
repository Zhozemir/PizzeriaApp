package com.example.pizzeria.services.impl;

import com.example.pizzeria.models.Product;
import com.example.pizzeria.repositories.interfaces.ProductDAO;
import com.example.pizzeria.services.exceptions.InvalidProductException;
import com.example.pizzeria.services.exceptions.ProductNotFoundException;
import com.example.pizzeria.services.exceptions.ProductProcessingException;
import com.example.pizzeria.services.interfaces.ProductService;
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
    public Product addProduct (String name, BigDecimal price){

        if(name == null || name.isBlank()){
            throw new InvalidProductException("Името на продукта не може да е празно.");
        }

        if(price == null || price.signum() <= 0){
            throw new InvalidProductException("Цената трябва да е положително число.");
        }

        Product product = new Product(name, price);
        boolean ok;

        try{
            ok = productDAO.save(product);
        } catch (Exception ex){
            throw new ProductProcessingException("Грешка при запис на продукт в базата.", ex);
        }

        if(!ok){
            throw new ProductProcessingException("Неуспешно добавяне на прдукта." , null);
        }

        return product;

    }

    @Override
    public List<Product> getActiveProducts() {
        return productDAO.findActiveProducts();
    }

    @Override
    public void deactivateProduct(Long productId){

        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.setActive(false);
        boolean ok;

        try{
            ok = productDAO.update(product);
        } catch (Exception ex){
            throw new ProductProcessingException("Грешка при деактивиране на продукт в базата.", ex);
        }

        if(!ok){
            throw new ProductProcessingException("Неуспешно деактивиране на продукт.", null);
        }
    }
}
