package com.example.pizzeria.controllers;

import com.example.pizzeria.controllers.requests.UserLoginRequest;
import com.example.pizzeria.controllers.requests.UserRegisterRequest;
import com.example.pizzeria.enumerators.UserRole;
import com.example.pizzeria.services.interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@Mock
    @Autowired
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void testRegisterWithValidationFailure() throws Exception {

        UserRegisterRequest request = new UserRegisterRequest("", "", null, "", "");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Грешни данни."));
    }

    @Test
    void testRegisterWithValidationSuccess() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest("user1", "pass", UserRole.CUSTOMER, "John Doe", "123456");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Регистрацията е успешна."));
    }

    @Test
    void testLoginWithValidationFailure() throws Exception {

        UserLoginRequest request = new UserLoginRequest("", "");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Грешни данни."));

    }

    @Test
    void testLoginWithValidationSuccess() throws Exception{

        userService.registerUser("user", "pass", UserRole.CUSTOMER, "John Doe", "123456");

        UserLoginRequest request = new UserLoginRequest("user", "pass");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

    }

}