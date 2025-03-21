package com.example.PizzeriaApp.console.validations;

import java.util.Scanner;

public class PhoneValidation {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readPhone(String message) {

        while(true) {

            System.out.print(message);

            String phone = scanner.nextLine().trim();

            if(!phone.matches("^\\+?\\d+$")){

                System.out.println("Грешка! Телефонният номер трябва да съдържа само цифри и по избор да започва с '+'. Опитайте отново.");
                continue;

            }

            return phone;
        }
    }
}
