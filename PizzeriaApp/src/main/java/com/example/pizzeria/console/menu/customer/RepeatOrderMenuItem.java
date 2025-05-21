package com.example.pizzeria.console.menu.customer;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.validations.IdValidation;

public class RepeatOrderMenuItem implements MenuItem {

    private final OrderController orderController;

    public RepeatOrderMenuItem(OrderController orderController) {
        this.orderController = orderController;
    }

    @Override
    public String text() {
        return "Повтори поръчка";
    }

    @Override
    public void execute() {

        try {

            String delivered = orderController.printMyDelivered();

            if (delivered.trim().startsWith("Няма")) {

                System.out.println("Няма завършени поръчки за повторение.");
                return;

            }

            System.out.println(delivered);
            long id = IdValidation.readId("Id за повторение: ");
            orderController.repeat(id);

        } catch (Exception e) {
            System.out.println("Грешка при повторение на поръчка.");
        }
    }
}
