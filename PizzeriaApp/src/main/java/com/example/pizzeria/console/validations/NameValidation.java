package com.example.pizzeria.console.validations;

import java.util.Scanner;

public class NameValidation {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readName(String message) {

        while (true) {

            System.out.print(message);

            String name = scanner.nextLine().trim();

            if(!name.matches("^[A-ZА-Я][a-zа-я]+(-[A-ZА-Я][a-zа-я]+)?( [A-ZА-Я][a-zа-я]+(-[A-ZА-Я][a-zа-я]+)?)*$")){

                System.out.println("Името трябва да започва с главна буква и да съдържа само малки букви след това. Опитайте отново.");
                continue;

            }

            return name;
        }
    }
}
