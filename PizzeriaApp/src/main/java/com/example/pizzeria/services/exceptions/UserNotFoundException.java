package com.example.pizzeria.services.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username){
        super("Потребител " + username + " не е намерен.");
    }
}
