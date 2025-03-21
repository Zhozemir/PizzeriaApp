package com.example.PizzeriaApp.console.validations;

import com.example.PizzeriaApp.enumerators.UserRole;

import java.util.Scanner;

public class RoleValidation {

    private static final Scanner scanner = new Scanner(System.in);

    public static UserRole readRole(String message) {

        while(true) {

            System.out.print(message + " (CUSTOMER / EMPLOYEE): ");
            String input = scanner.nextLine().trim().toUpperCase();

            try {
                return UserRole.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Невалидна роля. Опитайте отново.");
            }
        }
    }
}

