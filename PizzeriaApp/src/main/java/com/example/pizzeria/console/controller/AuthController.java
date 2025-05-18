package com.example.pizzeria.console.controller;

import com.example.pizzeria.console.ConsoleService;
import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.validations.NameValidation;
import com.example.pizzeria.console.validations.PasswordValidation;
import com.example.pizzeria.console.validations.PhoneValidation;
import com.example.pizzeria.console.validations.RoleValidation;
import com.example.pizzeria.controllers.requests.UserLoginRequest;
import com.example.pizzeria.controllers.requests.UserRegisterRequest;
import com.example.pizzeria.dto.UserDTO;
import com.example.pizzeria.enumerators.UserRole;
import com.example.pizzeria.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Scanner;

public class AuthController {

    private final ObjectMapper mapper = new ObjectMapper();

    public void login(Scanner scanner) {

        try {

            System.out.print("Потребителско име: ");
            String username = scanner.nextLine();
            String password = PasswordValidation.readPassword("Парола: ");
            String response = ConsoleService.postJson(
                    "/auth/login",
                    new UserLoginRequest(username, password)
            );

            UserDTO dto = mapper.readValue(response, UserDTO.class);
            User user = new User(dto.getId(), dto.getUsername(), dto.getRole(), dto.getName(), dto.getPhone());
            ConsoleSession.login(user);
            System.out.println("Успешен вход!");

        } catch (Exception e) {
            System.out.println("Грешно потребителско име или парола.");
        }
    }

    public void register(Scanner scanner) {

        try {

            String username;

            while (true) {

                System.out.print("Потребителско име: ");
                username = scanner.nextLine();
                String exists = ConsoleService.get("/auth/check/username?username=" + username);
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
            String response = ConsoleService.postJson(
                    "/auth/register",
                    new UserRegisterRequest(username, password, role, name, phone)
            );

            System.out.println(response);
        } catch (Exception e) {
            System.out.println("Грешка при регистрация.");
        }
    }

}
