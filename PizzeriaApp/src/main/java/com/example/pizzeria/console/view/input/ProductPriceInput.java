package com.example.pizzeria.console.view.input;

import com.example.pizzeria.console.validation.validators.ProductPriceValidator;
import com.example.pizzeria.console.validation.ValidationResult;
import com.example.pizzeria.console.validation.Validator;

import java.util.Scanner;

public class ProductPriceInput {

    private final Validator validator = new ProductPriceValidator();
    private final Scanner scanner = new Scanner(System.in);

    public double readProductPrice(String message) {

        while(true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            ValidationResult vr = validator.validate(input);

            if(vr.isValid())
                return Double.parseDouble(input);

            System.out.println(vr.getErrorMessage());

        }
    }

}
