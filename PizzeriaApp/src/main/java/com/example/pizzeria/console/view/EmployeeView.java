package com.example.pizzeria.console.view;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.controller.ProductController;
import com.example.pizzeria.console.model.AbstractMenuItem;
import com.example.pizzeria.console.validations.DateTimeValidation;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.dto.ProductDTO;

import java.util.List;
import java.util.Scanner;

public class EmployeeView {
    private final ProductController productCtrl = new ProductController();
    private final OrderController orderCtrl = new OrderController();
    private final Scanner scanner = new Scanner(System.in);

    public void show() {

        new MenuView("Служителско Меню", List.of(
                new AbstractMenuItem("Добави продукт") {

                    @Override
                    public void execute() {
                        productCtrl.add(scanner);
                    }
                },
                new AbstractMenuItem("Деактивирай продукт") {

                    @Override
                    public void execute() {

                        try {

                            List<ProductDTO> products = productCtrl.listActive();
                            if (products.isEmpty()) {
                                System.out.println("Няма активни продукти.");
                                return;
                            }
                            products.forEach(p ->
                                    System.out.printf("%d) %s – %.2f лв.%n", p.getId(), p.getName(), p.getPrice())
                            );
                            productCtrl.deactivate(scanner);
                        } catch (Exception e) {
                            System.out.println("Грешка при деактивиране на продукт.");
                        }
                    }
                },
                new AbstractMenuItem("Активни продукти") {

                    @Override
                    public void execute() {

                        try {
                            List<ProductDTO> list = productCtrl.listActive();
                            if (list.isEmpty()) System.out.println("Няма продукти.");
                            else list.forEach(p ->
                                    System.out.printf("%d) %s – %.2f лв.%n", p.getId(), p.getName(), p.getPrice())
                            );
                        } catch (Exception e) {
                            System.out.println("Грешка при зареждане на продукти.");
                        }

                    }
                },
                new AbstractMenuItem("Обнови статус") {

                    @Override
                    public void execute() {
                        try {

                            String table = orderCtrl.printAll();
                            if (table.trim().startsWith("Няма")) {
                                System.out.println("Няма поръчки.");
                                return;
                            }

                            System.out.println(table);
                            orderCtrl.updateStatus(scanner);
                        } catch (Exception e) {
                            System.out.println("Грешка при обновяване на статус.");
                        }
                    }
                },
                new AbstractMenuItem("Всички поръчки") {

                    @Override
                    public void execute() {

                        try {

                            String table = orderCtrl.printAll();
                            if (table.trim().startsWith("Няма"))
                                System.out.println("Няма поръчки.");
                            else
                                System.out.println(table);
                        } catch (Exception e) {
                            System.out.println("Грешка при зареждане на поръчки.");
                        }

                    }
                },
                new AbstractMenuItem("Справка по период") {

                    @Override public void execute() {

                        String allTable = orderCtrl.printAll();
                        if (allTable.trim().startsWith("Няма")) {

                            System.out.println("Няма никакви поръчки.");
                            return;

                        }

                        try {
                            String from = DateTimeValidation.readDateTime(
                                    "Въведете начална дата и час (например 2023-03-01T00:00): ");
                            String to = DateTimeValidation.readDateTime(
                                    "Въведете крайна дата и час (например 2023-03-31T23:59): ");
                            String report = orderCtrl.printPeriod(from, to);

                            if (report.trim().startsWith("Няма")) {
                                System.out.println("Няма поръчки за справка за този период.");
                            } else {
                                System.out.println(report);
                            }

                        } catch (Exception ex) {
                            System.out.println("Грешка при справката.");
                        }
                    }
                },
                new AbstractMenuItem("Изход") {

                    @Override
                    public void execute() {
                        ConsoleSession.logout();
                    }
                }
        )).render();
    }
}

