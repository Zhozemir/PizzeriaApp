package com.example.pizzeria.console.exceptions;

public class ConsoleServerException extends ConsoleException{

    public ConsoleServerException(String message){
        super(message);
    }

    public ConsoleServerException(String message, Throwable cause){
        super(message, cause);
    }

}
