package com.example.pizzeria.console.validations;

import java.util.Scanner;

public class ChoiceValidation {

    public static boolean isValid(String choice){
        return choice.matches("^[1-9]\\d*$");
    }

}
