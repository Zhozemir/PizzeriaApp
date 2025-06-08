package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.controller.ProductController;
import com.example.pizzeria.console.model.MenuItem;

import java.math.BigDecimal;
import java.util.Scanner;

public class AddProductMenuItem implements MenuItem {

    private final ProductController productController;
    private final Scanner scanner;

    public AddProductMenuItem(ProductController productController, Scanner scanner) {

        this.productController = productController;
        this.scanner = scanner;

    }

    @Override
    public String text() {
        return "Добави продукт";
    }

    @Override
    public void execute() {

        try {

            System.out.print("Име: ");
            String name = scanner.nextLine();

            System.out.print("Цена: ");
            String priceInput = scanner.nextLine();
            BigDecimal price = new BigDecimal(priceInput);

            productController.addProduct(name, price);

        } catch (Exception e) {
            System.out.println("Грешка при добавяне на продукт: " + e.getMessage());
        }

    }
}
