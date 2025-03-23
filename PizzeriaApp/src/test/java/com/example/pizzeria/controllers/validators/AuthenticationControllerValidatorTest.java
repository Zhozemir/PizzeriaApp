package com.example.pizzeria.controllers.validators;

import com.example.pizzeria.controllers.requests.UserLoginRequest;
import com.example.pizzeria.controllers.requests.UserRegisterRequest;
import com.example.pizzeria.enumerators.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationControllerValidatorTest {

    private final AuthenticationControllerValidator validator = new AuthenticationControllerValidator();

    @Test
    void testValidateUserRegisterWithInvalidData(){

        UserRegisterRequest request = new UserRegisterRequest("", "", null, "", "");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateUserRegister(request);
        });
        assertTrue(exception.getMessage().contains("Invalid UserRegister request"));

    }

    @Test
    void testValidateUserRegisterWithValidData() {

        UserRegisterRequest request = new UserRegisterRequest("user", "pass", UserRole.CUSTOMER, "John", "123456");
        assertDoesNotThrow(() -> validator.validateUserRegister(request));

    }

    @Test
    void testValidateUserLoginWithInvalidData() {

        UserLoginRequest request = new UserLoginRequest("", "");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateUserLogin(request);
        });

        assertTrue(exception.getMessage().contains("Invalid UserLogin request"));
    }

    @Test
    void testValidateUserLoginWithValidData() {

        UserLoginRequest request = new UserLoginRequest("user", "pass");
        assertDoesNotThrow(() -> validator.validateUserLogin(request));

    }

}
