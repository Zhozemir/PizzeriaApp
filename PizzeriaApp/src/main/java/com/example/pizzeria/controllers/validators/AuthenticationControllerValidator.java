package com.example.pizzeria.controllers.validators;

import com.example.pizzeria.controllers.requests.UserLoginRequest;
import com.example.pizzeria.controllers.requests.UserRegisterRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationControllerValidator {

    public void validateUserRegister(UserRegisterRequest userRegisterRequest) {

        if (StringUtils.isEmpty(userRegisterRequest.getUsername()))
            throw new IllegalArgumentException(String.format("Invalid UserRegister request [userRegisterRequest: %s]",
                    userRegisterRequest));

        if (StringUtils.isEmpty(userRegisterRequest.getPassword()))
            throw new IllegalArgumentException(String.format("Invalid UserRegister request [userRegisterRequest: %s]",
                    userRegisterRequest));

        if ( (userRegisterRequest.getRole() == null)
                || !( (userRegisterRequest.getRole().toString().toUpperCase().equals("EMPLOYEE")) || (userRegisterRequest.getRole().toString().toUpperCase().equals("CUSTOMER")) ) )
            throw new IllegalArgumentException(String.format("Invalid UserRegister request [userRegisterRequest: %s]",
                    userRegisterRequest));

        if (StringUtils.isEmpty(userRegisterRequest.getName()))
            throw new IllegalArgumentException(String.format("Invalid UserRegister request [userRegisterRequest: %s]",
                    userRegisterRequest));

        if (userRegisterRequest.getPhone().isEmpty())
            throw new IllegalArgumentException(String.format("Invalid UserRegister request [userRegisterRequest: %s]",
                    userRegisterRequest));

    }

    public void validateUserLogin(UserLoginRequest userLoginRequest){

        if (StringUtils.isEmpty(userLoginRequest.getUsername()))
            throw new IllegalArgumentException(String.format("Invalid UserLogin request [userLoginRequest: %s]",
                    userLoginRequest));

        if (StringUtils.isEmpty(userLoginRequest.getPassword()))
            throw new IllegalArgumentException(String.format("Invalid UserLogin request [userLoginRequest: %s]",
                    userLoginRequest));

    }

}

