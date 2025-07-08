package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validation.validators.RoleValidator;
import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;
import com.example.pizzeria.enumerators.UserRole;

import java.util.Scanner;

public class RoleInput {

    private final Validator validator = new RoleValidator();
    private final Scanner scanner = new Scanner(System.in);

    public UserRole readRole(String message){

        while(true){

            System.out.println(message + " CUSTOMER / EMPLOYEE: ");

            String input = scanner.nextLine().trim();

            ValidationResult vr = validator.validate(input);

            if(vr.isValid())
                return UserRole.valueOf(input.toUpperCase());

            System.out.println(vr.getErrorMessage());

        }

    }

}