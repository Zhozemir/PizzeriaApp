package com.example.pizzeria.services.interfaces;

import com.example.pizzeria.enumerators.UserRole;
import com.example.pizzeria.models.User;

import java.util.Optional;

public interface UserService {

    void registerUser(String username, String password, UserRole role, String name, String phone);
    Optional<User> login(String username, String password);
    Optional<User> findByUsername(String username);

}

