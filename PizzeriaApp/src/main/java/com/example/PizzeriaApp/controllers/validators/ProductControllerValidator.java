package com.example.PizzeriaApp.controllers.validators;

import com.example.PizzeriaApp.controllers.requests.ProductCreateRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductControllerValidator {

    public boolean createProductValidator(ProductCreateRequest productCreateRequest){

        if(productCreateRequest == null || productCreateRequest.getName() == null || productCreateRequest.getName().isEmpty())
            return false;

        if(productCreateRequest.getPrice() == null || productCreateRequest.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            return false;

        return true;
    }

    public boolean validateDeactivateProduct(Long id){

        return id != null;

    }

}
