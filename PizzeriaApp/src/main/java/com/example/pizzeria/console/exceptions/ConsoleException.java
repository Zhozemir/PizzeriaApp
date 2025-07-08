package com.example.pizzeria.console.exceptions;

import com.example.pizzeria.console.ConsoleSession;

public class ConsoleException extends RuntimeException{

    public ConsoleException(String message){
        super(message);
    }

    public ConsoleException(String message, Throwable cause){
        super(message, cause);
    }

}
