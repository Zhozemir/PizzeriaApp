package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validation.validators.IdValidator;
import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

import java.util.Scanner;

public class IdInput {

    private final Validator validator = new IdValidator();
    private final Scanner scanner = new Scanner(System.in);

    public Long readId(String message){

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            ValidationResult vr = validator.validate(input);

            if(vr.isValid())
                return Long.parseLong(input);

            System.out.println(vr.getErrorMessage());

        }

    }

}
