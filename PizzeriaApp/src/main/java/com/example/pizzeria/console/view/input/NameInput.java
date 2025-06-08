package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validations.NameValidation;

import java.util.Scanner;

public class NameInput {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readName(String message){

        while(true){

            System.out.print(message);

            String name = scanner.nextLine().trim();

            if(NameValidation.isValid(name)){
                return name;
            }

            System.out.println("Името трябва да започва с главна буква и да съдържа само малки букви след това. Опитайте отново.");

        }

    }



}
