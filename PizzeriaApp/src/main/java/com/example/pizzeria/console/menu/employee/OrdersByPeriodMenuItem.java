package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.exceptions.ConsoleServerException;
import com.example.pizzeria.console.exceptions.ConsoleValidationException;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.view.input.DateTimeInput;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.printing.OrderDTOPrinter;

import java.util.List;

public class OrdersByPeriodMenuItem implements MenuItem {

    private final OrderController orderController;
    private final DateTimeInput dateTimeInput = new DateTimeInput();

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

            List<OrderDTO> allOrders = orderController.getAllOrders();

            if (allOrders.isEmpty()) {

                System.out.println("Няма никакви поръчки.");
                return;

            }

            String from = dateTimeInput.readDateTime("Начална дата (Пример: 2023-03-01T00:00): ");
            String to = dateTimeInput.readDateTime("Крайна дата (Пример: 2023-03-01T00:59): ");

            List<OrderDTO> periodOrders = orderController.getOrdersByPeriod(from, to);

            if (periodOrders.isEmpty()) {
                System.out.println("Няма поръчки в този период.");
                return;
            }

            String table = OrderDTOPrinter.getPrintedOrders(periodOrders);
            System.out.println(table);

        } catch (ConsoleValidationException | ConsoleServerException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex){
            System.out.println("Неочаквана грешка.");
        }
    }
}