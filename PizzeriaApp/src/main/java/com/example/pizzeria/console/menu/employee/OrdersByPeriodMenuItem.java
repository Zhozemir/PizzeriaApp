package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.validations.DateTimeValidation;

public class OrdersByPeriodMenuItem implements MenuItem {

    private final OrderController orderController;

    public OrdersByPeriodMenuItem(OrderController orderController) {
        this.orderController = orderController;
    }

    @Override
    public String text() {
        return "Справка по период";
    }

    @Override
    public void execute() {

        try {

            String allTable = orderController.printAll();

            if (allTable.trim().startsWith("Няма")) {

                System.out.println("Няма никакви поръчки.");
                return;

            }

            String from = DateTimeValidation.readDateTime("Начална дата (Пример: 2023-03-01T00:00): ");
            String to = DateTimeValidation.readDateTime("Крайна дата (Пример: 2023-03-01T00:59): ");
            String report = orderController.printPeriod(from, to);

            System.out.println(report);

        } catch (Exception e) {
            System.out.println("Грешка при справката.");
        }
    }
}