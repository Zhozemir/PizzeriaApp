package com.example.pizzeria.console.validations;

import java.util.Scanner;

public class IdValidation {

    private static final Scanner scanner = new Scanner(System.in);

    public static Long readId(String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.matches("\\d+")) {

                System.out.println("Невалидно ID. Опитайте отново.");
                continue;

            }

            return Long.parseLong(input);

        }
    }

}

