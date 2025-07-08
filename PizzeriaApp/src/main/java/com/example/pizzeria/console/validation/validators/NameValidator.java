package com.example.pizzeria.console.validation.validators;

import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

public class NameValidator implements Validator {

    private static final String REGEX = "^[A-ZА-Я][a-zа-я]+(-[A-ZА-Я][a-zа-я]+)?( [A-ZА-Я][a-zа-я]+(-[A-ZА-Я][a-zа-я]+)?)*$";

    @Override
    public ValidationResult validate(String input){

        if(input != null && input.matches(REGEX)){
            return ValidationResult.ok();
        }

        return ValidationResult.fail("Името трябва да започва с главна буква и да съдържа само малки букви след това. Опитайте отново.");

    }

}
