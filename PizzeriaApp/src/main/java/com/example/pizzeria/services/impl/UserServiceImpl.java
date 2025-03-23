package com.example.PizzeriaApp.services.impl;

import com.example.PizzeriaApp.domain.exceptions.UserAlreadyExistsException;
import com.example.PizzeriaApp.enumerators.UserRole;
import com.example.PizzeriaApp.models.User;
import com.example.PizzeriaApp.repositories.interfaces.UserDAO;
import com.example.PizzeriaApp.security.PasswordHashing;
import com.example.PizzeriaApp.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public void registerUser(String username, String password, UserRole role, String name, String phone) {

        Optional<User> existingUser = userDAO.findByUsername(username);

       // if(existingUser.isPresent())
            //return false;

        if(existingUser.isPresent())
            throw new UserAlreadyExistsException();

        String hashedPassword = PasswordHashing.hashPassword(password);
        User user = new User(username, hashedPassword, role, name, phone);

        userDAO.save(user);

    }

    @Override
    public Optional<User> login(String username, String password) {

        Optional<User> userOpt = userDAO.findByUsername(username);

        if(userOpt.isPresent() && PasswordHashing.matchPassword(password, userOpt.get().getPassword()))
            return userOpt;

        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

}
