package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validations.RoleValidation;
import com.example.pizzeria.enumerators.UserRole;

import java.util.Scanner;

public class RoleInput {

    private static final Scanner scanner = new Scanner(System.in);

    public static UserRole readRole(String message) {

        while(true) {

            System.out.print(message + " (CUSTOMER / EMPLOYEE): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if(RoleValidation.isValid(input)){
                return UserRole.valueOf(input);
            }

            System.out.println("Невалидна роля. Опитайте отново.");

        }
    }

}
