package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.model.MenuItem;

import java.util.Scanner;

public class UpdateOrderStatusMenuItem implements MenuItem {

    private final OrderController orderController;
    private final Scanner scanner;

    public UpdateOrderStatusMenuItem(OrderController orderController, Scanner scanner) {

        this.orderController = orderController;
        this.scanner = scanner;

    }

    @Override
    public String text() {
        return "Обнови статус";
    }

    @Override
    public void execute() {

        try {

            String table = orderController.printAll();

            if (table.trim().startsWith("Няма")) {

                System.out.println("Няма поръчки.");
                return;

            }

            System.out.println(table);
            orderController.updateStatus(scanner);

        } catch (Exception e) {
            System.out.println("Грешка при обновяване на статус.");
        }
    }
}
