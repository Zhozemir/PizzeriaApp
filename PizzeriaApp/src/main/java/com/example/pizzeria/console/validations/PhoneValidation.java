package com.example.pizzeria.console.validations;

import java.util.Scanner;

public class PhoneValidation {

    public static boolean isValid(String phone){
        return phone.matches("^\\+?\\d+$");
    }

}
