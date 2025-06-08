package com.example.pizzeria.console.view;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.NotificationService;
import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.controller.ProductController;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.menu.customer.CreateOrderMenuItem;
import com.example.pizzeria.console.menu.customer.LogoutMenuItem;
import com.example.pizzeria.console.menu.customer.MyOrderMenuItem;
import com.example.pizzeria.console.menu.customer.RepeatOrderMenuItem;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.notifications.OrderDeliveredListener;
import com.example.pizzeria.console.validations.IdValidation;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.dto.ProductDTO;

import java.util.List;
import java.util.Scanner;

public class CustomerView implements OrderDeliveredListener {

    private final ProductController productCtrl;
    private final OrderController orderCtrl;
    private final Scanner scanner = new Scanner(System.in);
    private final NotificationService notifier;

    public CustomerView(ProductController productCtrl, OrderController orderCtrl) {

        this.productCtrl = productCtrl;
        this.orderCtrl = orderCtrl;
        this.notifier = new NotificationService(orderCtrl);
        this.notifier.addListener(this);

    }

    public void show() {

        notifier.start();

        List<MenuItem> menuItems = List.of(
                new CreateOrderMenuItem(productCtrl, orderCtrl, scanner),
                new MyOrderMenuItem(orderCtrl),
                new RepeatOrderMenuItem(orderCtrl),
                new LogoutMenuItem()
        );

        new MenuView("Клиентско Меню", menuItems).handleMenuInteraction();
    }

    @Override
    public void onOrderDelivered(long orderId){
        System.out.printf("%nПоръчка %d е доставена.", orderId);
    }

}