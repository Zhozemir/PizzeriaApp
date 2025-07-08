package com.example.pizzeria.services.exceptions;

public class ProductProcessingException extends RuntimeException {

    public ProductProcessingException(String message, Throwable cause){
        super(message, cause);
    }
}
