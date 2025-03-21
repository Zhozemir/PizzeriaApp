package com.example.PizzeriaApp.controllers;

import com.example.PizzeriaApp.controllers.validators.AuthenticationControllerValidator;
import com.example.PizzeriaApp.dto.UserDTO;
import com.example.PizzeriaApp.controllers.requests.UserLoginRequest;
import com.example.PizzeriaApp.controllers.requests.UserRegisterRequest;
import com.example.PizzeriaApp.mappers.UserMapper;
import com.example.PizzeriaApp.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        boolean isValid = authenticationControllerValidator.validateUserRegister(userRegisterRequest);

        if (!isValid)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Грешни данни.");

        boolean success = userService.registerUser(
                userRegisterRequest.getUsername(),
                userRegisterRequest.getPassword(),
                userRegisterRequest.getRole(),
                userRegisterRequest.getName(),
                userRegisterRequest.getPhone()
        );

        return success
                ? ResponseEntity.ok("Регистрацията е успешна")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Потребител с това име вече съществува");

    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginRequest userLoginRequest) {

        boolean isValid = authenticationControllerValidator.validateUserLogin(userLoginRequest);

        if(!isValid) {

            return ResponseEntity.badRequest().build();
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Грешни данни");

        }

        return userService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword())
                .map(user -> ResponseEntity.ok(userMapper.toDTO(user)))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

    }


    @GetMapping("/check/username")
    public ResponseEntity<String> checkUsername(@RequestParam String username) {

        return userService.findByUsername(username).isPresent()
                ? ResponseEntity.ok("exists")
                : ResponseEntity.ok("not exists");

    }
}
