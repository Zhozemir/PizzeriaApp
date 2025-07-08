package com.example.pizzeria.console.validation.validators;

import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;
import com.example.pizzeria.enumerators.UserRole;

public class RoleValidator implements Validator {

    @Override
    public ValidationResult validate (String input){

        if(input == null){
            return ValidationResult.fail("Невалидна роля. Опитайте отново.");
        }

        try{

            UserRole.valueOf(input.toUpperCase());
            return ValidationResult.ok();

        } catch (IllegalArgumentException e){
            return ValidationResult.fail("Невалидна роля. Опитайте отново.");
        }

    }

}

