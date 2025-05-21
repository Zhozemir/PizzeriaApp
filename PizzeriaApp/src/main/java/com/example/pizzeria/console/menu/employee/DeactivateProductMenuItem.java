package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.controller.ProductController;
import com.example.pizzeria.console.model.MenuItem;

import java.util.Scanner;

public class DeactivateProductMenuItem implements MenuItem {

    private final ProductController productController;
    private final Scanner scanner;

    public DeactivateProductMenuItem(ProductController productController, Scanner scanner) {

        this.productController = productController;
        this.scanner = scanner;

    }

    @Override
    public String text() {
        return "Деактивирай продукт";
    }

    @Override
    public void execute() {

        try {
            productController.deactivate(scanner);
        } catch (Exception e) {
            System.out.println("Грешка при деактивиране на продукт.");
        }
    }
}
