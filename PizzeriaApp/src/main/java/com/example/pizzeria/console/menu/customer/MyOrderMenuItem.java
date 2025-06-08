package com.example.pizzeria.console.menu.customer;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.printing.OrderDTOPrinter;

import java.util.List;

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

            List<OrderDTO> myOrders = orderController.getMyOrders();

            if (myOrders.isEmpty()) {

                System.out.println("Нямате поръчки.");
                return;

            }

            String table = OrderDTOPrinter.getPrintedOrders(myOrders);
            System.out.println(table);

        } catch (Exception e) {
            System.out.println("Грешка при зареждане на поръчките.");
        }
    }
}