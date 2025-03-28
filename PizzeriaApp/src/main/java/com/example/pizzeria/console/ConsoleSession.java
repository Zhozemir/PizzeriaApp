package com.example.pizzeria.console;

import com.example.pizzeria.enumerators.UserRole;
import com.example.pizzeria.models.User;

public class ConsoleSession {

    private static User currentUser;

    public static void login(User user){
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static UserRole getRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }

}

