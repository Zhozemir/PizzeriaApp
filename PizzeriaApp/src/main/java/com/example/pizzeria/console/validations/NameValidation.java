package com.example.pizzeria.console.validations;

import java.util.Scanner;

public class NameValidation {

    public static boolean isValid(String name){
        return name.matches("^[A-ZА-Я][a-zа-я]+(-[A-ZА-Я][a-zа-я]+)?( [A-ZА-Я][a-zа-я]+(-[A-ZА-Я][a-zа-я]+)?)*$");
    }

}
