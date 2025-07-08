package com.example.pizzeria.console.controller;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.exceptions.ConsoleServerException;
import com.example.pizzeria.console.exceptions.ConsoleValidationException;
import com.example.pizzeria.console.http.ApiResponse;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.utils.ErrorResponseUtil;
import com.example.pizzeria.controllers.requests.UserLoginRequest;
import com.example.pizzeria.controllers.requests.UserRegisterRequest;
import com.example.pizzeria.dto.UserDTO;
import com.example.pizzeria.enumerators.UserRole;
import com.example.pizzeria.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AuthController {

    private final HttpClientService httpClientService;
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthController(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }

    public boolean checkUsernameExists(String username) {

        if (username == null || username.isBlank()) {
            throw new ConsoleValidationException("Потребителското име не може да е празно.");
        }

        String encoded = URLEncoder.encode(username, StandardCharsets.UTF_8);
        ApiResponse resp;

        try {
            resp = httpClientService.get("/auth/check/username?username=" + encoded);
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при свързване със сървъра: " + e.getMessage(), e);
        }

        switch (resp.getStatusCode()) {

            case 200: return true;
            case 404: return false;
            default:
                String err = ErrorResponseUtil.extract(resp);
                throw new ConsoleServerException("Неуспешна проверка на потребителско име: " + err);

        }
    }

    public void register(String username, String password, UserRole role, String name, String phone) {

        if (username   == null || username.isBlank())
            throw new ConsoleValidationException("Потребителското име не може да е празно.");

        if (password   == null || password.isBlank())
            throw new ConsoleValidationException("Паролата не може да е празна.");

        if (role       == null)
            throw new ConsoleValidationException("Ролята е задължителна.");

        if (name       == null || name.isBlank())
            throw new ConsoleValidationException("Името не може да е празно.");

        if (phone      == null || phone.isBlank())
            throw new ConsoleValidationException("Телефонът не може да е празен.");


        if (checkUsernameExists(username)) {
            throw new ConsoleValidationException("Потребител с това име вече съществува.");
        }

        UserRegisterRequest req = new UserRegisterRequest(username, password, role, name, phone);
        ApiResponse resp;

        try {
            resp = httpClientService.postJson("/auth/register", req);
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при връзка със сървъра: " + e.getMessage(), e);
        }

        if (resp.getStatusCode() != 201) {

            String err = ErrorResponseUtil.extract(resp);
            throw new ConsoleServerException("Регистрацията е неуспешна: " + err);

        }
    }

    public User login(String username, String password) {

        if (username == null || username.isBlank())
            throw new ConsoleValidationException("Потребителското име не може да е празно.");

        if (password == null || password.isBlank())
            throw new ConsoleValidationException("Паролата не може да е празна.");

        ApiResponse resp;

        try {
            resp = httpClientService.postJson("/auth/login", new UserLoginRequest(username, password));
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при връзка със сървъра: " + e.getMessage(), e);
        }

        if (resp.getStatusCode() != 200) {

            String err = ErrorResponseUtil.extract(resp);
            throw new ConsoleServerException("Грешен вход: " + err);

        }

        UserDTO dto;

        try {
            dto = mapper.readValue(resp.getBody(), UserDTO.class);
        } catch (Exception e) {
            throw new ConsoleServerException("Грешка при обработка на отговор: " + e.getMessage(), e);
        }

        User user = new User(dto.getId(), dto.getUsername(), dto.getRole(), dto.getName(), dto.getPhone());
        ConsoleSession.login(user);
        return user;

    }
}
