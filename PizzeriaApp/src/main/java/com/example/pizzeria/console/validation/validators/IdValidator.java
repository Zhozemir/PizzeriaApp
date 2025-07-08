package com.example.pizzeria.console.validation.validators;

import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

public class IdValidator implements Validator {

    @Override
    public ValidationResult validate(String input) {

        if(input != null && input.matches("\\d+")){
            return ValidationResult.ok();
        }

        return ValidationResult.fail("Невалидно ID. Опитайте отново.");

    }

}

