package com.example.pizzeria.services.exceptions;

public class OrderProcessingException extends RuntimeException {
    public OrderProcessingException(String message, Throwable cause){
        super(message, cause);
    }
}
