package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validation.validators.PhoneValidator;
import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

import java.util.Scanner;

public class PhoneInput {

    private final Validator validator = new PhoneValidator();
    private final Scanner scanner = new Scanner(System.in);

    public String readPhone(String message) {

        while(true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            ValidationResult vr = validator.validate(input);

            if(vr.isValid())
                return input;

            System.out.println(vr.getErrorMessage());

        }
    }

}
