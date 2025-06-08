package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.printing.OrderDTOPrinter;

import java.util.List;
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

            List<OrderDTO> allOrders = orderController.getAllOrders();

            if (allOrders.isEmpty()) {

                System.out.println("Няма поръчки.");
                return;

            }

            String table = OrderDTOPrinter.getPrintedOrders(allOrders);
            System.out.println(table);

            System.out.print("ID на поръчка: ");
            long id = Long.parseLong(scanner.nextLine());

            System.out.print("Нов статус (IN_PROGRESS, DELIVERED, CANCELLED): ");
            String status = scanner.nextLine().toUpperCase();

            orderController.updateOrderStatus(id, status);

        } catch (Exception e) {
            System.out.println("Грешка при обновяване на статус.");
        }
    }
}
