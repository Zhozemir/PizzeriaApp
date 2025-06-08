package com.example.pizzeria.console.validations;

import java.util.Scanner;

public class IdValidation {

    public static boolean isValid(String id){
        return id.matches("\\d+");
    }

}

