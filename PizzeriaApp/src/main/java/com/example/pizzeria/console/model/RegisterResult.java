package com.example.pizzeria.console.model;

public class RegisterResult {

    private final boolean success;
    private final String message;

    public RegisterResult(boolean success, String message) {

        this.success = success;
        this.message = message;

    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
