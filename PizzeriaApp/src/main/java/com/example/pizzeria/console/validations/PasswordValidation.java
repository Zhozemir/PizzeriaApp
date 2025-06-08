package com.example.pizzeria.console.validations;

import java.util.Scanner;

public class PasswordValidation {

    public static boolean isValid(String password){
        return !password.trim().isEmpty();
    }

}

