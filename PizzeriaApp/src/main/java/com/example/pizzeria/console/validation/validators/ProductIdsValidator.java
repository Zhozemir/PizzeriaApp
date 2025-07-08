package com.example.pizzeria.console.validation.validators;

import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

public class ProductIdsValidator implements Validator {

    public static boolean isValid(String ids){
        return ids.matches("(\\d+,)*\\d+");
    }

    @Override
    public ValidationResult validate(String input){

        if(input != null && input.matches("(\\d+,)*\\d+")){
            return ValidationResult.ok();
        }

        return ValidationResult.fail("Невалидни ID-та. Моля, въведете само числа, разделени със запетая. Пример: 1,2,3");

    }

}