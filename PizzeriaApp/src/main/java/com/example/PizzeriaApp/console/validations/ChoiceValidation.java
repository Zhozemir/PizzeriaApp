package com.example.PizzeriaApp.console.validations;

import java.util.Scanner;

public class ChoiceValidation {

    private static final Scanner scanner = new Scanner(System.in);

    public static int readChoice(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            if(!input.matches("^[1-9]\\d*$")){

                System.out.println("Невалиден избор.");
                continue;

            }

            return Integer.parseInt(input);
        }
    }
}
