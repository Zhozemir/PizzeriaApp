package com.example.pizzeria.console;

import com.example.pizzeria.console.controller.AuthController;
import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.controller.ProductController;
import com.example.pizzeria.console.http.HttpClientService;
import com.example.pizzeria.console.view.AuthView;
import com.example.pizzeria.console.view.CustomerView;
import com.example.pizzeria.console.view.EmployeeView;
import com.example.pizzeria.enumerators.UserRole;

public class ConsoleApp {

    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(2000);
        // изчакване за сървъра

        HttpClientService httpClient = new HttpClientService();

        AuthController authController = new AuthController(httpClient);
        OrderController orderController = new OrderController(httpClient);
        ProductController productController = new ProductController(httpClient);

        AuthView authView = new AuthView(authController);
        CustomerView customerView = new CustomerView(productController, orderController);
        EmployeeView employeeView = new EmployeeView(productController, orderController);

        NotificationService notifier = new NotificationService(orderController);

        boolean notifierStarted = false;

        while (true) {

            if (!ConsoleSession.isLoggedIn()) {

                authView.show();

                if(ConsoleSession.isLoggedIn() && !notifierStarted){

                    notifier.start();
                    notifierStarted = true;

                }

            } else if (ConsoleSession.getRole() == UserRole.CUSTOMER) {
                customerView.show();
            } else {
                employeeView.show();
            }
        }

    }

}
