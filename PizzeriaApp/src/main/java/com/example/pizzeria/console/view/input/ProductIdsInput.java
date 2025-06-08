package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validations.ProductIdsValidation;

import java.util.Scanner;

public class ProductIdsInput {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readProductIds(String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            if(ProductIdsValidation.isValid(input)){
                return input;
            }

            System.out.println("Невалидни ID-та. Моля, въведете само числа, разделени със запетая. Пример: 1,2,3");

        }
    }

}
