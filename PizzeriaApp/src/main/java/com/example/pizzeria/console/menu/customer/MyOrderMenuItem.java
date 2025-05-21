package com.example.pizzeria.console.menu.customer;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.model.MenuItem;

public class MyOrderMenuItem implements MenuItem {

    private final OrderController orderController;

    public MyOrderMenuItem(OrderController orderController) {
        this.orderController = orderController;
    }

    @Override
    public String text() {
        return "Моите поръчки";
    }

    @Override
    public void execute() {

        try {

            String result = orderController.printMy();
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Грешка при зареждане на поръчките.");
        }
    }
}