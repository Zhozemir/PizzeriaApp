package com.example.pizzeria.console.validations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateTimeValidation {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static boolean isValid(String input){

        try{

            LocalDateTime.parse(input, FORMATTER);
            return true;

        } catch (DateTimeParseException e){
            return false;
        }

    }

}

