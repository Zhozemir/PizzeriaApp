package com.example.pizzeria.console.menu.customer;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.validations.IdValidation;
import com.example.pizzeria.console.view.input.IdInput;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.printing.OrderDTOPrinter;

import java.util.List;

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

            List<OrderDTO> deliveredOrders = orderController.getMyDeliveredOrders();

            if(deliveredOrders.isEmpty()){

                System.out.println("Няма завършени поръчки за повторение.");
                return;

            }

            String table = OrderDTOPrinter.getPrintedOrders(deliveredOrders);
            System.out.println(table);

            long id = IdInput.readId("Id за повторение: ");
            orderController.repeatOrder(id);

        } catch (Exception e) {
            System.out.println("Грешка при повторение на поръчка.");
        }
    }
}
