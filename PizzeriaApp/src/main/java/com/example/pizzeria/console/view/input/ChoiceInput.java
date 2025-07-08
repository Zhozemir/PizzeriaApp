package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validation.validators.ChoiceValidator;
import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

import java.util.Scanner;

public class ChoiceInput {

    private final Validator validator = new ChoiceValidator();
    private final Scanner scanner = new Scanner(System.in);

    public int readChoice(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            ValidationResult vr = validator.validate(input);

            if(vr.isValid())
                return Integer.parseInt(input);

            System.out.println(vr.getErrorMessage());

        }
    }

}
