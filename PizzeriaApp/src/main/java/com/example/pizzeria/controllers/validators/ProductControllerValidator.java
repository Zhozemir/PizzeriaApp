package com.example.pizzeria.controllers.validators;

import com.example.pizzeria.controllers.requests.ProductCreateRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductControllerValidator {

    public void createProductValidator(ProductCreateRequest productCreateRequest){

        if(productCreateRequest == null || productCreateRequest.getName() == null || productCreateRequest.getName().isEmpty()
                || productCreateRequest.getPrice() == null || productCreateRequest.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException(String.format("Invalid ProductCreate request [productCreateRequest: %s]",
                    productCreateRequest));

    }

    public void validateDeactivateProduct(Long id){

        if(id == null)
            throw  new IllegalArgumentException("Невалидни данни за деактивиране на продукта.");

    }

}
