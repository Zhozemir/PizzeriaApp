package com.example.pizzeria.console.menu.customer;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.exceptions.ConsoleServerException;
import com.example.pizzeria.console.exceptions.ConsoleValidationException;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.view.input.IdInput;
import com.example.pizzeria.console.view.input.PhoneInput;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.printing.OrderDTOPrinter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RepeatOrderMenuItem implements MenuItem {

    private final OrderController orderController;

    private final IdInput idInput = new IdInput();

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

            Set<Long> validIds = deliveredOrders.stream()
                    .map(OrderDTO::getId)
                    .collect(Collectors.toSet());

            long id;

            while(true){

                id = idInput.readId("Id за повторение: ");

                if (validIds.contains(id))
                    break;

                System.out.println("Невалидно ID. Да се избере едно от показаните.");

            }

            orderController.repeatOrder(id);

        } catch (ConsoleValidationException | ConsoleServerException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex){
            System.out.println("Неочаквана грешка.");
        }
    }
}
