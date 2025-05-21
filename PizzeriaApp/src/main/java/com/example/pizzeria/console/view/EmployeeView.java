package com.example.pizzeria.console.view;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.controller.ProductController;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.menu.employee.*;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.validations.DateTimeValidation;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.dto.ProductDTO;

import java.util.List;
import java.util.Scanner;

public class EmployeeView {

    private final ProductController productCtrl;
    private final OrderController orderCtrl;
    private final Scanner scanner = new Scanner(System.in);

    public EmployeeView(ProductController productCtrl, OrderController orderCtrl) {

        this.productCtrl = productCtrl;
        this.orderCtrl = orderCtrl;

    }

    public void show() {

        List<MenuItem> menuItems = List.of(
                new AddProductMenuItem(productCtrl, scanner),
                new DeactivateProductMenuItem(productCtrl, scanner),
                new ActiveProductsMenuItem(productCtrl),
                new UpdateOrderStatusMenuItem(orderCtrl, scanner),
                new AllOrdersMenuItem(orderCtrl),
                new OrdersByPeriodMenuItem(orderCtrl),
                new LogoutMenuItem()
        );

        new MenuView("Служителско Меню", menuItems).handleMenuInteraction();
    }
}
