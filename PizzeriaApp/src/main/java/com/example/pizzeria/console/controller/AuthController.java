package com.example.pizzeria.console.controller;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.http.ApiResponse;
import com.example.pizzeria.console.http.ErrorResponse;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.model.LoginResult;
import com.example.pizzeria.console.model.RegisterResult;
import com.example.pizzeria.console.utils.ErrorResponseUtil;
import com.example.pizzeria.controllers.requests.UserLoginRequest;
import com.example.pizzeria.controllers.requests.UserRegisterRequest;
import com.example.pizzeria.dto.UserDTO;
import com.example.pizzeria.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthController {

    private final HttpClientService httpClientService;
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthController(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }

    public LoginResult login(String username, String password) {

        try {

            ApiResponse response = httpClientService.postJson(
                    "/auth/login",
                    new UserLoginRequest(username, password)
            );

            if (response.isSuccess()) {

                UserDTO dto = mapper.readValue(response.getBody(), UserDTO.class);
                User user = new User(dto.getId(), dto.getUsername(), dto.getRole(), dto.getName(), dto.getPhone());
                ConsoleSession.login(user);
                return new LoginResult(true, "Успешен вход!", user);

            } else {

                String message = ErrorResponseUtil.extract(response);
                return new LoginResult(false, message, null);

            }

        } catch (Exception e) {
            return new LoginResult(false, "Грешка при вход: " + e.getMessage(), null);
        }
    }

    public RegisterResult register(UserRegisterRequest req) {

        try {

            ApiResponse checkResponse = httpClientService.get("/auth/check/username?username=" + req.getUsername());

            if (checkResponse.isSuccess() && "exists".equalsIgnoreCase(checkResponse.getBody().trim())) {
                return new RegisterResult(false, "Потребител с това име вече съществува.");
            }

            ApiResponse registerResponse = httpClientService.postJson("/auth/register", req);

            if (registerResponse.isSuccess()) {
                return new RegisterResult(true, "Регистрацията е успешна.");
            } else {

                String message = ErrorResponseUtil.extract(registerResponse);
                return new RegisterResult(false, message);

            }

        } catch (Exception e) {
            return new RegisterResult(false, "Грешка при регистрация: " + e.getMessage());
        }
    }

    public String checkUsername(String username) {
        return httpClientService.get("/auth/check/username?username=" + username).getBody();
    }
}