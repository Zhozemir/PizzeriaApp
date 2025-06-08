package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validations.PasswordValidation;

import java.util.Scanner;

public class PasswordInput {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readPassword(String message) {

        while (true) {

            System.out.print(message);

            String password = scanner.nextLine();

            if(PasswordValidation.isValid(password)){
                return password;
            }

            System.out.println("Невалидна парола. Опитайте отново.");

        }
    }

}
