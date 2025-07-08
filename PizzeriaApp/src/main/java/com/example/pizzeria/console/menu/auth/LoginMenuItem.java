package com.example.pizzeria.console.menu.auth;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.controller.AuthController;
import com.example.pizzeria.console.exceptions.ConsoleServerException;
import com.example.pizzeria.console.exceptions.ConsoleValidationException;
import com.example.pizzeria.console.model.LoginResult;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.view.input.PasswordInput;
import com.example.pizzeria.models.User;

import java.util.Scanner;

public class LoginMenuItem implements MenuItem {

    private final AuthController controller;
    private final Scanner scanner;

    private final PasswordInput passwordInput = new PasswordInput();

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
        String password = passwordInput.readPassword("Парола: ");

        try {

            User user = controller.login(username, password);
            ConsoleSession.login(user);
            System.out.println("Успешен вход!");

        } catch (ConsoleValidationException ex) {
            System.out.println("Невалидни данни: " + ex.getMessage());
        } catch (ConsoleServerException ex) {
            System.out.println("Грешка при свързване със сървъра: " + ex.getMessage());
        }

    }
}
