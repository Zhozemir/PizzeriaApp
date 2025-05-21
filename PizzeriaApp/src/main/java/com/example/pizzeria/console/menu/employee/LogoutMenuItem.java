package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.model.MenuItem;

public class LogoutMenuItem implements MenuItem {

    @Override
    public String text() {
        return "Изход";
    }

    @Override
    public void execute() {
        ConsoleSession.logout();
    }
}