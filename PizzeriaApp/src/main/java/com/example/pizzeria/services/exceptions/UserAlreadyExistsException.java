package com.example.pizzeria.services.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String username){
        super("Потребител " + username + " вече съществува.");
    }
}
