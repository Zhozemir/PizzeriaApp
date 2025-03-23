package com.example.pizzeria.printing;

import com.example.pizzeria.models.Order;
import de.vandermeer.asciitable.AsciiTable;
import com.example.pizzeria.models.Product;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPrinter {

    // генерира се ascii таблица
    public static String getPrintedOrders(List<Order> orders) {

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Поръчка ID", "Статус", "Създадена на", "Продукти");
        at.addRule();

        for (Order order : orders) {

            String products = order.getProducts().stream()
                    .map(Product::toString)
                    .collect(Collectors.joining(", "));

            at.addRow(order.getId(), order.getStatus(), order.getCreatedOn(), products);
            at.addRule();

        }

        return at.render();
    }
}

