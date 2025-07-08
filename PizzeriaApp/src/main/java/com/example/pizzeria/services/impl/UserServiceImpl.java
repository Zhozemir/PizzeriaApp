package com.example.pizzeria.services.impl;

import com.example.pizzeria.enumerators.UserRole;
import com.example.pizzeria.models.User;
import com.example.pizzeria.repositories.DataAccessException;
import com.example.pizzeria.repositories.interfaces.UserDAO;
import com.example.pizzeria.security.PasswordHashing;
import com.example.pizzeria.services.exceptions.AuthenticationException;
import com.example.pizzeria.services.exceptions.UserAlreadyExistsException;
import com.example.pizzeria.services.exceptions.UserNotFoundException;
import com.example.pizzeria.services.exceptions.ValidationException;
import com.example.pizzeria.services.interfaces.UserService;
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
    public User registerUser(String username, String password, UserRole role, String name, String phone) {

        if(username == null || username.isBlank())
            throw new ValidationException("Потребителското име е задължително.");

        if(userDAO.findByUsername(username).isPresent())
            throw new UserAlreadyExistsException(username);

        String hashedPassword  = PasswordHashing.hashPassword(password);
        User user = new User(username, hashedPassword, role, name, phone);

        try{

            boolean saved = userDAO.save(user);

            if(!saved){
                throw new org.springframework.dao.DataAccessException("Неуспешно записване на потребител.") {};
            }

        } catch (DataAccessException ex){
            throw ex;
        } catch (Exception ex){
            throw new DataAccessException("Грешка при запис на нов потребител.", ex);
        }

        return user;

    }

    @Override
    public User authenticate(String username, String password) {

        Optional<User> userOpt;

        try{
            userOpt = userDAO.findByUsername(username);
        } catch (Exception ex){
            throw new DataAccessException("Грешка при достъп до данни.", ex) {};
        }

        User user  = userOpt.orElseThrow(AuthenticationException::new);

        if(!PasswordHashing.matchPassword(password, user.getPassword())){
            throw new AuthenticationException();
        }

        return user;

    }

    @Override
    public User getByUsername (String username) {

        try{
            return userDAO.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException(username));
        } catch (UserNotFoundException ex){
            throw ex;
        } catch (Exception ex){
            throw new DataAccessException("Грешка при достъп до данни.", ex) {};
        }
    }

}
