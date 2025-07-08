package com.example.pizzeria.services.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id){
        super("Поръчка с ID=" + id + " не е намерена.");
    }
}
