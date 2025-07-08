package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.controller.ProductController;
import com.example.pizzeria.console.exceptions.ConsoleServerException;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.dto.ProductDTO;

import java.util.List;

public class ActiveProductsMenuItem implements MenuItem {

    private final ProductController productController;

    public ActiveProductsMenuItem(ProductController productController) {
        this.productController = productController;
    }

    @Override
    public String text() {
        return "Активни продукти";
    }

    @Override
    public void execute() {

        try {

            List<ProductDTO> list = productController.listActive();

            if (list.isEmpty()) {
                System.out.println("Няма продукти.");
            }
            else {
                list.forEach(p -> System.out.printf("%d) %s – %.2f лв.%n", p.getId(), p.getName(), p.getPrice()));
            }
        } catch (ConsoleServerException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex){
            System.out.println("Неочаквана грешка.");
        }
    }
}