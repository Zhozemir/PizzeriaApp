package com.example.pizzeria.console.menu.customer;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.controller.ProductController;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.dto.ProductDTO;

import java.util.List;
import java.util.Scanner;

public class CreateOrderMenuItem implements MenuItem {

    private final ProductController productController;
    private final OrderController orderController;
    private final Scanner scanner;

    public CreateOrderMenuItem(ProductController productController, OrderController orderController, Scanner scanner) {

        this.productController = productController;
        this.orderController = orderController;
        this.scanner = scanner;

    }

    @Override
    public String text() {
        return "Създай поръчка";
    }

    @Override
    public void execute() {

        try {

            List<ProductDTO> products = productController.listActive();

            if (products.isEmpty()) {

                System.out.println("Няма активни продукти.");
                return;

            }

            products.forEach(p -> System.out.printf("%d) %s – %.2f лв.%n", p.getId(), p.getName(), p.getPrice()));

            System.out.print("ID-та (разделени със запетая): ");
            var input = scanner.nextLine();
            var ids = OrderController.parseIds(input);
            orderController.create(ids);

        } catch (Exception e) {
            System.out.println("Грешка при създаване на поръчка.");
        }
    }
}
