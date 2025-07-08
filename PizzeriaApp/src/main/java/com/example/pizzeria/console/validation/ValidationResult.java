package com.example.pizzeria.console.validation;

public class ValidationResult {

    private final boolean valid;
    private final String errorMessage;

    private ValidationResult(boolean valid, String errorMessage){

        this.valid = valid;
        this.errorMessage = errorMessage;

    }

    public static ValidationResult ok(){
        return new ValidationResult(true, null);
    }

    public static ValidationResult fail(String message){
        return new ValidationResult(false, message);
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

}
