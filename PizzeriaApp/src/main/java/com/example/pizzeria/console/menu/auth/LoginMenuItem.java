package com.example.pizzeria.console.menu.auth;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.controller.AuthController;
import com.example.pizzeria.console.model.LoginResult;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.validations.PasswordValidation;
import com.example.pizzeria.console.view.input.PasswordInput;

import java.util.Scanner;

public class LoginMenuItem implements MenuItem {

    private final AuthController controller;
    private final Scanner scanner;

    public LoginMenuItem(AuthController controller, Scanner scanner) {

        this.controller = controller;
        this.scanner = scanner;

    }

    @Override
    public String text() {
        return "Вход";
    }

    @Override
    public void execute() {

        System.out.print("Потребителско име: ");
        String username = scanner.nextLine();
        String password = PasswordInput.readPassword("Парола: ");

        LoginResult result = controller.login(username, password);

        if (result.isSuccess()) {

            ConsoleSession.login(result.getUser());
            System.out.println(result.getMessage());

        } else {
            System.out.println(result.getMessage());
        }
    }
}
