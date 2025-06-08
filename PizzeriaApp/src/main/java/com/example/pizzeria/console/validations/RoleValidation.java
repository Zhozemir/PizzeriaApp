package com.example.pizzeria.console.validations;

import com.example.pizzeria.enumerators.UserRole;

public class RoleValidation {

    public static boolean isValid(String input){

        if(input == null)
            return false;

        try{
            UserRole.valueOf(input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }

    }

}

