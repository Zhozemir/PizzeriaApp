package com.example.pizzeria.console.validations;

import java.util.Scanner;

public class ProductIdsValidation {

    public static boolean isValid(String ids){
        return ids.matches("(\\d+,)*\\d+");
    }

}