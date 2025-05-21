package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.model.MenuItem;

public class AllOrdersMenuItem implements MenuItem {

    private final OrderController orderController;

    public AllOrdersMenuItem(OrderController orderController) {
        this.orderController = orderController;
    }

    @Override
    public String text() {
        return "Всички поръчки";
    }

    @Override
    public void execute() {

        try {

            String result = orderController.printAll();
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Грешка при зареждане на поръчки.");
        }
    }
}