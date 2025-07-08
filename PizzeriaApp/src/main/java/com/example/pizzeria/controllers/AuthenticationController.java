package com.example.pizzeria.controllers;

import com.example.pizzeria.controllers.validators.AuthenticationControllerValidator;
import com.example.pizzeria.dto.UserDTO;
import com.example.pizzeria.controllers.requests.UserLoginRequest;
import com.example.pizzeria.controllers.requests.UserRegisterRequest;
import com.example.pizzeria.mappers.UserMapper;
import com.example.pizzeria.models.User;
import com.example.pizzeria.services.exceptions.AuthenticationException;
import com.example.pizzeria.services.exceptions.UserNotFoundException;
import com.example.pizzeria.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<UserDTO> register(@RequestBody UserRegisterRequest userRegisterRequest) {

        authenticationControllerValidator.validateUserRegister(userRegisterRequest);

        User created = userService.registerUser(
                userRegisterRequest.getUsername(),
                userRegisterRequest.getPassword(),
                userRegisterRequest.getRole(),
                userRegisterRequest.getName(),
                userRegisterRequest.getPhone()
        );

        UserDTO dto = userMapper.toDTO(created);

        URI location = URI.create("/api/auth" + dto.getId());

        return ResponseEntity.created(location).body(dto);

    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginRequest userLoginRequest) {

        authenticationControllerValidator.validateUserLogin(userLoginRequest);

        try{

            User user = userService.authenticate(userLoginRequest.getUsername(), userLoginRequest.getPassword());

            UserDTO dto = userMapper.toDTO(user);

            return ResponseEntity.ok(dto);

        } catch (AuthenticationException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @GetMapping("/check/username")
    public ResponseEntity<String> checkUsername(@RequestParam String username) {

        try{

            userService.getByUsername(username);
            return ResponseEntity.ok().build();

        } catch (UserNotFoundException ex){
            return ResponseEntity.notFound().build();
        }

    }

}
