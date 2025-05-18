package com.example.pizzeria.console.view;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.controller.ProductController;
import com.example.pizzeria.console.model.AbstractMenuItem;
import com.example.pizzeria.console.validations.IdValidation;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.dto.ProductDTO;

import java.util.List;
import java.util.Scanner;

public class CustomerView {

    private final ProductController productCtrl = new ProductController();
    private final OrderController orderCtrl = new OrderController();
    private final Scanner scanner = new Scanner(System.in);

    public void show() {

        new MenuView("Клиентско Меню", List.of(
                new AbstractMenuItem("Създай поръчка") {
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

                            System.out.print("ID-та (разделени със запетая): ");
                            var ids = OrderController.parseIds(scanner.nextLine());
                            orderCtrl.create(ids);

                        } catch (Exception e) {
                            System.out.println("Грешка при създаване на поръчка.");
                        }
                    }
                },
                new AbstractMenuItem("Моите поръчки") {

                    @Override
                    public void execute() {

                        try {
                            String table = orderCtrl.printMy();
                            if (table.trim().startsWith("Няма")) {
                                System.out.println("Няма поръчки.");
                            } else {
                                System.out.println(table);
                            }
                        } catch (Exception e) {
                            System.out.println("Грешка при зареждане на поръчките.");
                        }
                    }
                },
                new AbstractMenuItem("Повтори поръчка") {

                    @Override
                    public void execute() {
                        try {

                            String table = orderCtrl.printMyDelivered();

                            if (table.trim().startsWith("Няма")) {
                                System.out.println("Няма завършени поръчки за повторение.");
                                return;
                            }
                            System.out.println(table);

                            long id = IdValidation.readId("Id за повторение: ");
                            orderCtrl.repeat(id);
                        } catch (Exception e) {
                            System.out.println("Грешка при повторение на поръчка.");
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
