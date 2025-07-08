package com.example.pizzeria.console.validation.validators;

import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeValidator implements Validator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    public ValidationResult validate(String input){

        try{

            LocalDateTime.parse(input, FORMATTER);
            return ValidationResult.ok();

        } catch (DateTimeParseException e){
            return ValidationResult.fail("Невалиден формат за дата/час. Пример: 2023-03-01T00:00");
        }

    }

}

