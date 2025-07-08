package com.example.pizzeria.services.exceptions;

public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String message){
        super(message);
    }
}
