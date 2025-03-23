package com.example.pizzeria.console.validations;

import java.util.Scanner;

public class ProductIdsValidation {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readProductIds(String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.matches("(\\d+,)*\\d+")) {

                System.out.println("Невалидни ID-та. Моля, въведете само числа, разделени със запетая. Пример: 1,2,3");
                continue;

            }

            return input;
        }
    }

}