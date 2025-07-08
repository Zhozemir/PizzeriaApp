package com.example.pizzeria.console.validation.validators;

import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

public class ProductPriceValidator implements Validator {

    @Override
    public ValidationResult validate(String input){

        if(input != null && input.matches("^(\\d+(\\.\\d*)?|\\.\\d+)$")){
            return ValidationResult.ok();
        }

        return ValidationResult.fail("Невалидна стойност за цена на продукт. Пример: 1, 5, 5.11. Опитайте отново.");

    }

}