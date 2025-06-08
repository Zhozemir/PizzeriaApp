package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validations.IdValidation;

import java.util.Scanner;

public class IdInput {

    private static final Scanner scanner = new Scanner(System.in);

    public static Long readId(String message){

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if(IdValidation.isValid(input)){
                return Long.parseLong(input);
            }

            System.out.println("Невалидно ID. Опитайте отново.");

        }

    }

}
