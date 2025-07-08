package com.example.pizzeria.services.exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String message){
        super(message);
    }
}
