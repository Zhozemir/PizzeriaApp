package com.example.pizzeria.console.menu.auth;

import com.example.pizzeria.console.controller.AuthController;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.model.RegisterResult;
import com.example.pizzeria.console.validations.NameValidation;
import com.example.pizzeria.console.validations.PasswordValidation;
import com.example.pizzeria.console.validations.PhoneValidation;
import com.example.pizzeria.console.validations.RoleValidation;
import com.example.pizzeria.controllers.requests.UserRegisterRequest;
import com.example.pizzeria.enumerators.UserRole;

import java.util.Scanner;

public class RegisterMenuItem implements MenuItem {

    private final AuthController controller;
    private final Scanner scanner;

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

        try {

            String username;

            while (true) {

                System.out.print("Потребителско име: ");
                username = scanner.nextLine();

                String exists = controller.checkUsername(username);

                if ("exists".equalsIgnoreCase(exists.trim())) {
                    System.out.println("Потребител с това име вече съществува.");
                } else {
                    break;
                }
            }

            String password = PasswordValidation.readPassword("Парола: ");
            String name = NameValidation.readName("Име: ");
            String phone = PhoneValidation.readPhone("Телефон: ");
            UserRole role = RoleValidation.readRole("Роля");

            UserRegisterRequest req = new UserRegisterRequest(username, password, role, name, phone);
            RegisterResult result = controller.register(req);

            System.out.println(result.getMessage());

        } catch (Exception e) {
            System.out.println("Грешка при регистрация.");
        }
    }
}
