package com.example.PizzeriaApp.controllers.validators;


import com.example.PizzeriaApp.controllers.requests.UserLoginRequest;
import com.example.PizzeriaApp.controllers.requests.UserRegisterRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationControllerValidator {

    public boolean validateUserRegister(UserRegisterRequest userRegisterRequest) {

        if (StringUtils.isEmpty(userRegisterRequest.getUsername()))
            return false;

        if (StringUtils.isEmpty(userRegisterRequest.getPassword()))
            return false;

        if ( (userRegisterRequest.getRole() == null)
                || !( (userRegisterRequest.getRole().toString().toUpperCase().equals("EMPLOYEE")) || (userRegisterRequest.getRole().toString().toUpperCase().equals("CUSTOMER")) ) )
            return false;

        if (StringUtils.isEmpty(userRegisterRequest.getName()))
            return false;

        if (userRegisterRequest.getPhone().isEmpty())
            return false;

        return true;
    }

    public boolean validateUserLogin(UserLoginRequest userLoginRequest){

        if (StringUtils.isEmpty(userLoginRequest.getUsername()))
            return false;

        if (StringUtils.isEmpty(userLoginRequest.getPassword()))
            return false;

        return true;
    }

}

