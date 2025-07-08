package com.example.pizzeria.console.menu.auth;

import com.example.pizzeria.console.controller.AuthController;
import com.example.pizzeria.console.exceptions.ConsoleServerException;
import com.example.pizzeria.console.exceptions.ConsoleValidationException;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.model.RegisterResult;
import com.example.pizzeria.console.view.input.NameInput;
import com.example.pizzeria.console.view.input.PasswordInput;
import com.example.pizzeria.console.view.input.PhoneInput;
import com.example.pizzeria.console.view.input.RoleInput;
import com.example.pizzeria.controllers.requests.UserRegisterRequest;
import com.example.pizzeria.enumerators.UserRole;

import java.util.Scanner;

public class RegisterMenuItem implements MenuItem {

    private final AuthController controller;
    private final Scanner scanner;

    private final PasswordInput passwordInput = new PasswordInput();
    private final NameInput nameInput = new NameInput();
    private final PhoneInput phoneInput = new PhoneInput();
    private final RoleInput roleInput = new RoleInput();

    public RegisterMenuItem(AuthController controller, Scanner scanner) {

        this.controller = controller;
        this.scanner = scanner;

    }

    @Override
    public String text() {
        return "Регистрация";
    }

    @Override
    public void execute() {

        String username;
        while (true) {

            System.out.print("Потребителско име: ");
            username = scanner.nextLine().trim();

            try {

                String exists = String.valueOf(controller.checkUsernameExists(username));

                if ("exists".equalsIgnoreCase(exists.trim())) {
                    System.out.println("Потребител с това име вече съществува.");
                } else {
                    break;
                }

            } catch (ConsoleServerException e) {

                System.out.println("Грешка при проверка на потребителско име: " + e.getMessage());
                return;

            }
        }

        String password;
        while (true) {

            try {

                password = passwordInput.readPassword("Парола: ");
                break;

            } catch (ConsoleValidationException e) {
                System.out.println(e.getMessage());
            }
        }

        String name;
        while (true) {

            try {

                name = nameInput.readName("Име: ");
                break;

            } catch (ConsoleValidationException e) {
                System.out.println(e.getMessage());
            }

        }

        String phone;
        while (true) {

            try {

                phone = phoneInput.readPhone("Телефон: ");
                break;

            } catch (ConsoleValidationException e) {
                System.out.println(e.getMessage());
            }

        }

        UserRole role;
        while (true) {

            try {

                role = roleInput.readRole("Роля");
                break;

            } catch (ConsoleValidationException e) {
                System.out.println(e.getMessage());
            }

        }

        try {

            controller.register(username, password, role, name, phone);
            System.out.println("Регистрацията е успешна.");

        } catch (ConsoleServerException e) {
            System.out.println("Грешка при регистрация: " + e.getMessage());
        }

    }

}
