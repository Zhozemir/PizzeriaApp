package com.example.pizzeria.console.validation.validators;

import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

public class PhoneValidator implements Validator {

    @Override
    public ValidationResult validate(String input){

        if(input != null && input.matches("^\\+?\\d+$")){
            return ValidationResult.ok();
        }

        return ValidationResult.fail("Грешка! Телефонният номер трябва да съдържа само цифри и по избор да започва с '+'. Опитайте отново.");

    }

}
