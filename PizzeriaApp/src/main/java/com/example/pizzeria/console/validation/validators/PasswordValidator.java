package com.example.pizzeria.console.validation.validators;

import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

public class PasswordValidator implements Validator {

    @Override
    public ValidationResult validate (String input){

        if(input != null && input.matches("^(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-\\[\\]{};:'\"\\\\|,.<>/?]).{8,}$")){
            return ValidationResult.ok();
        }

        return ValidationResult.fail("Невалидна парола. Изискване: поне 8 символа, поне една цифра, поне един специален символ.");

    }

}

