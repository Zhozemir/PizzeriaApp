package com.example.PizzeriaApp.console.validations;

import java.util.Scanner;

public class PasswordValidation {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readPassword(String message) {

        while (true) {

            System.out.print(message);

            String password = scanner.nextLine();

            if(password.trim().isEmpty()){

                System.out.println("Невалидна парола. Опитайте отново.");
                continue;

            }

            return password;
        }
    }
}

