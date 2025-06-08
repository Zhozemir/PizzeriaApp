package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validations.DateTimeValidation;

import java.util.Scanner;

public class DateTimeInput {

      private static final Scanner scanner = new Scanner(System.in);

      public static String readDateTime(String message) {

         while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            if(DateTimeValidation.isValid(input)){
                return input;
            }

             System.out.println("Невалиден формат за дата/час. Пример: 2023-03-01T00:00");

         }
    }

}
