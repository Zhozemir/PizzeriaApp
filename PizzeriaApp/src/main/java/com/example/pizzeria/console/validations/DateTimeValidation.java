package com.example.pizzeria.console.validations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateTimeValidation {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static String readDateTime(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {

                LocalDateTime.parse(input, FORMATTER);
                return input;

            } catch (DateTimeParseException e) {
                System.out.println("Невалиден формат за дата/час. Пример: 2023-03-01T00:00");
            }
        }
    }

}

