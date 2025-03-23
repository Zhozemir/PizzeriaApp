package com.example.PizzeriaApp.controllers.exceptionhandlers;

import com.example.PizzeriaApp.controllers.AuthenticationController;
import com.example.PizzeriaApp.domain.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {AuthenticationController.class})
public class AuthenticationControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException  e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Грешни данни.");
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Потребител с това име вече съществува.");
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Обща грешка.");
    }


}
