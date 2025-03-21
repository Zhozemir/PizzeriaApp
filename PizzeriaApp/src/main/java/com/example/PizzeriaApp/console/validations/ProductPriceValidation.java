package com.example.PizzeriaApp.console.validations;

import java.util.Scanner;

public class ProductPriceValidation {

    private static final Scanner scanner = new Scanner(System.in);

    public static double readProductPrice(String message) {

        while(true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if(!input.matches("^(\\d+(\\.\\d*)?|\\.\\d+)$")){

                System.out.println("Невалидна стойност за цена на продукт. Опитайте отново.");
                continue;

            }

            return Double.parseDouble(input);
        }
    }
}