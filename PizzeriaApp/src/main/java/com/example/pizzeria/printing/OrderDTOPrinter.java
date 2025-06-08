package com.example.pizzeria.printing;

import com.example.pizzeria.dto.OrderDTO;
import de.vandermeer.asciitable.AsciiTable;

import java.util.List;

public class OrderDTOPrinter {

    public static String getPrintedOrders(List<OrderDTO> orders) {

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Поръчка ID", "Статус", "Създадена на", "Продукти");
        at.addRule();

        for (OrderDTO order : orders) {

            String products = order.getProductNames() == null || order.getProductNames().isEmpty()
                    ? "няма"
                    : String.join(", ", order.getProductNames());

            at.addRow(order.getId(), order.getStatus(), order.getCreatedOn(), products);
            at.addRule();

        }

        return at.render();
    }

}
