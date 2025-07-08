package com.example.pizzeria.services.exceptions;

public class InvalidProductException extends RuntimeException {

    public InvalidProductException(String message){
        super(message);
    }
}
