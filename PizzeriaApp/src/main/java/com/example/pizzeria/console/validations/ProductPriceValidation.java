package com.example.pizzeria.console.validations;

import java.util.Scanner;

public class ProductPriceValidation {

    public static boolean isValid(String productPrice){
        return productPrice.matches("^(\\d+(\\.\\d*)?|\\.\\d+)$");
    }

}