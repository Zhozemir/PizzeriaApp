package com.example.pizzeria.console.view;

import com.example.pizzeria.console.controller.AuthController;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.menu.auth.ExitMenuItem;
import com.example.pizzeria.console.menu.auth.LoginMenuItem;
import com.example.pizzeria.console.menu.auth.RegisterMenuItem;
import com.example.pizzeria.console.model.MenuItem;


import java.util.List;
import java.util.Scanner;

public class AuthView {

    private final AuthController controller;
    private final Scanner scanner = new Scanner(System.in);

    public AuthView(AuthController controller) {
        this.controller = controller;
    }

    public void show() {

        List<MenuItem> items = List.of(
                new LoginMenuItem(controller, scanner),
                new RegisterMenuItem(controller, scanner),
                new ExitMenuItem()
        );

        new MenuView("Добре дошли в Пицарията", items).handleMenuInteraction();
    }
}