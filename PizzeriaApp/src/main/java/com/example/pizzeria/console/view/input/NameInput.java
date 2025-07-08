package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validation.validators.NameValidator;
import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

import java.util.Scanner;

public class NameInput {

    private final Validator validator = new NameValidator();
    private final Scanner scanner = new Scanner(System.in);

    public String readName(String message){

        while(true){

            System.out.print(message);

            String input = scanner.nextLine().trim();

            ValidationResult vr = validator.validate(input);

            if(vr.isValid())
                return input;

            System.out.println(vr.getErrorMessage());

        }

    }



}
