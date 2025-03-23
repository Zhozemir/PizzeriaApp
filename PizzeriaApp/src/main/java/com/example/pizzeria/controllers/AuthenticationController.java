package com.example.pizzeria.controllers;

import com.example.pizzeria.controllers.validators.AuthenticationControllerValidator;
import com.example.pizzeria.dto.UserDTO;
import com.example.pizzeria.controllers.requests.UserLoginRequest;
import com.example.pizzeria.controllers.requests.UserRegisterRequest;
import com.example.pizzeria.mappers.UserMapper;
import com.example.pizzeria.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// endpoint tests???...

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationControllerValidator authenticationControllerValidator;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationControllerValidator authenticationControllerValidator, UserMapper userMapper) {

        this.userService = userService;
        this.authenticationControllerValidator = authenticationControllerValidator;
        this.userMapper = userMapper;

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest userRegisterRequest) {

        authenticationControllerValidator.validateUserRegister(userRegisterRequest);

        userService.registerUser(
                userRegisterRequest.getUsername(),
                userRegisterRequest.getPassword(),
                userRegisterRequest.getRole(),
                userRegisterRequest.getName(),
                userRegisterRequest.getPhone()
        );

        return ResponseEntity.ok("Регистрацията е успешна.");

    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginRequest userLoginRequest) {

        authenticationControllerValidator.validateUserLogin(userLoginRequest);

        return userService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword())
                .map(user -> ResponseEntity.ok(userMapper.toDTO(user)))
                .orElseThrow(()->new IllegalArgumentException("Грешно потребителско име или парола."));

    }

    @GetMapping("/check/username")
    public ResponseEntity<String> checkUsername(@RequestParam String username) {

        return userService.findByUsername(username).isPresent()
                ? ResponseEntity.ok("exists")
                : ResponseEntity.ok("not exists");

    }



}
