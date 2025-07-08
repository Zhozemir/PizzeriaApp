package com.example.pizzeria.console.validation.validators;

import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

public class ChoiceValidator implements Validator {

    @Override
    public ValidationResult validate(String input){

        if(input != null && input.matches("^[1-9]\\d*$")){
            return ValidationResult.ok();
        }

        return ValidationResult.fail("Невалиден избор");

    }

}
