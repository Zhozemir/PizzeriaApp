package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validation.validators.DateTimeValidator;
import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

import java.util.Scanner;

public class DateTimeInput {

    private final Validator validator = new DateTimeValidator();
    private static final Scanner scanner = new Scanner(System.in);


    public String readDateTime(String message) {

         while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

             ValidationResult vr = validator.validate(input);

             if(vr.isValid())
                 return input;

             System.out.println(vr.getErrorMessage());

         }
    }

}
