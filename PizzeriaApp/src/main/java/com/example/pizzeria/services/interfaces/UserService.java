package com.example.pizzeria.services.interfaces;

import com.example.pizzeria.enumerators.UserRole;
import com.example.pizzeria.models.User;

import java.util.Optional;

public interface UserService {

    User registerUser (String username, String password, UserRole role, String name, String phone);
    User authenticate (String username, String password);
    User getByUsername(String username);

}

