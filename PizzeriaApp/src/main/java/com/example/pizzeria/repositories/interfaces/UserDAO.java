package com.example.pizzeria.repositories.interfaces;

import com.example.pizzeria.models.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> findByUsername(String username);
    boolean save(User user);

}
