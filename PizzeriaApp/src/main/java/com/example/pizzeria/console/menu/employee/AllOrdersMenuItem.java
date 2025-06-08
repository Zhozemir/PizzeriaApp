package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.printing.OrderDTOPrinter;

import java.util.List;

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

            List<OrderDTO> allOrders = orderController.getAllOrders();

            if (allOrders.isEmpty()) {

                System.out.println("Няма никакви поръчки.");
                return;

            }

            String table = OrderDTOPrinter.getPrintedOrders(allOrders);
            System.out.println(table);

        } catch (Exception e) {
            System.out.println("Грешка при зареждане на поръчки.");
        }
    }
}