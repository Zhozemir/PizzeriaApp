package com.example.PizzeriaApp.repositories.interfaces;

import com.example.PizzeriaApp.models.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> findByUsername(String username);
    boolean save(User user);

}
