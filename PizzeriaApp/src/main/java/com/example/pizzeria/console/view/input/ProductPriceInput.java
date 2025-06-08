package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validations.ProductPriceValidation;

import java.util.Scanner;

public class ProductPriceInput {

    private static final Scanner scanner = new Scanner(System.in);

    public static double readProductPrice(String message) {

        while(true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if(ProductPriceValidation.isValid(input)){
                return Double.parseDouble(input);
            }

            System.out.println("Невалидна стойност за цена на продукт. Опитайте отново.");

        }
    }

}
