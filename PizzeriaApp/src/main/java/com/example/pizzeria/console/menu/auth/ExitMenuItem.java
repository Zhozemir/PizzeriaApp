package com.example.pizzeria.console.menu.auth;

import com.example.pizzeria.console.model.MenuItem;

public class ExitMenuItem implements MenuItem {

    @Override
    public String text() {
        return "Изход";
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}