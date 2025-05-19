package com.example.pizzeria.console;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.view.AuthView;
import com.example.pizzeria.console.view.CustomerView;
import com.example.pizzeria.console.view.EmployeeView;
import com.example.pizzeria.enumerators.UserRole;

public class ConsoleApp {

    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(2000);
        // изчакване за сървъра

        AuthView authView = new AuthView();
        CustomerView customerView = new CustomerView();
        EmployeeView employeeView = new EmployeeView();

        OrderController orderCtrl = new OrderController();
        NotificationService notifier = new NotificationService(orderCtrl);

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
