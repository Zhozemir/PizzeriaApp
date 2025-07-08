package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validation.validators.PasswordValidator;
import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

import java.util.Scanner;

public class PasswordInput {

    private final Validator validator = new PasswordValidator();
    private final Scanner scanner = new Scanner(System.in);

    public String readPassword(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            ValidationResult vr = validator.validate(input);

            if(vr.isValid())
                return input;

            System.out.println(vr.getErrorMessage());

        }
    }

}
