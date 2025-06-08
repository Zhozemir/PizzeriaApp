package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validations.PhoneValidation;

import java.util.Scanner;

public class PhoneInput {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readPhone(String message) {

        while(true) {

            System.out.print(message);

            String phone = scanner.nextLine().trim();

            if(PhoneValidation.isValid(phone)){
                return phone;
            }

            System.out.println("Грешка! Телефонният номер трябва да съдържа само цифри и по избор да започва с '+'. Опитайте отново.");

        }
    }

}
