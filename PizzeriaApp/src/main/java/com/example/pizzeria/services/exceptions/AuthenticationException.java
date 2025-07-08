package com.example.pizzeria.services.exceptions;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(){
        super("Грешно потребителско име или парола.");
    }
}
