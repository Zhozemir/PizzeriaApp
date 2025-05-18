package com.example.pizzeria.console.view;

import com.example.pizzeria.console.controller.AuthController;
import com.example.pizzeria.console.model.AbstractMenuItem;
import com.example.pizzeria.console.model.MenuItem;

import java.util.List;
import java.util.Scanner;

public class AuthView {

    private final AuthController controller = new AuthController();
    private final Scanner scanner  = new Scanner(System.in);

    public void show(){

        new MenuView("Добре дошли в Пицарията", List.of(
                new AbstractMenuItem("Вход") {

                    @Override
                    public void execute() {
                        controller.login(scanner);
                    }
                },
                new AbstractMenuItem("Регистрация") {

                    @Override
                    public void execute() {
                        controller.register(scanner);
                    }
                },
                new AbstractMenuItem("Изход") {

                    @Override
                    public void execute() {
                        System.exit(0);
                    }
                }
        )).render();

    }

}
