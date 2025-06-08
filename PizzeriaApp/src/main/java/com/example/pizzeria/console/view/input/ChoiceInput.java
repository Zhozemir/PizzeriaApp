package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validations.ChoiceValidation;

import java.util.Scanner;

public class ChoiceInput {

    private static final Scanner scanner = new Scanner(System.in);

    public static int readChoice(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            if(ChoiceValidation.isValid(input)){
                return Integer.parseInt(input);
            }

            System.out.println("Невалиден избор.");

        }
    }

}
