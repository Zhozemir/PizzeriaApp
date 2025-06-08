package com.example.pizzeria.console.view;

import com.example.pizzeria.console.model.MenuItem;
import com.example.pizzeria.console.validations.ChoiceValidation;
import com.example.pizzeria.console.view.input.ChoiceInput;

import java.util.List;
import java.util.Scanner;

public class MenuView {

    private final String title;
    private final List<MenuItem> items;
    private final Scanner scanner = new Scanner(System.in);

    public MenuView(String title, List<MenuItem> items){

        this.title = title;
        this.items = items;

    }

    private void displayMenu() {

        System.out.printf("%n--- %s ---%n", title);

        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, items.get(i).text());
        }

    }

    private int readUserChoice() {

        int choice = ChoiceInput.readChoice("Избор: ");

        if (choice < 1 || choice > items.size()) {

            System.out.println("Невалиден избор.");
            return -1;

        }

        return choice;
    }

    private void executeChoice(int choice) {

        if (choice == -1)
            return;

        items.get(choice - 1).execute();
    }

    public void handleMenuInteraction() {

        displayMenu();
        int choice = readUserChoice();
        executeChoice(choice);

    }

}
