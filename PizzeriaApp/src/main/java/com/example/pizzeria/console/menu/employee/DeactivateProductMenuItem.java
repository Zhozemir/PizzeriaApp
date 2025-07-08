package com.example.pizzeria.console.menu.employee;

import com.example.pizzeria.console.controller.ProductController;
import com.example.pizzeria.console.exceptions.ConsoleServerException;
import com.example.pizzeria.console.exceptions.ConsoleValidationException;
import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.dto.ProductDTO;

import java.util.List;
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

            List<ProductDTO> activeProducts = productController.listActive();

            if(activeProducts.isEmpty()){
                System.out.println("Няма активни продукти за деактивиране.");
                return;
            }

            System.out.println("Активни продукти:");
            activeProducts.forEach(p -> System.out.printf("%d) %s – %.2f лв.%n", p.getId(), p.getName(), p.getPrice()));

            System.out.print("ID на продукт за деактивиране: ");
            String idInput = scanner.nextLine().trim();
            long id = Long.parseLong(idInput);

            productController.deactivateProduct(id);

        } catch (ConsoleValidationException | ConsoleServerException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex){
            System.out.println("Неочаквана грешка.");
        }
    }
}
