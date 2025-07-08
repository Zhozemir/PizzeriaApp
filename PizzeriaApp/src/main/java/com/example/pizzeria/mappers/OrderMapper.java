package com.example.pizzeria.mappers;

import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Product;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public OrderDTO toDTO(Order order) {

        OrderDTO dto = new OrderDTO();

        dto.setId(order.getId());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedOn(order.getCreatedOn().format(FORMATTER));

        if(dto.getDeliveredOn() != null){
            dto.setDeliveredOn(dto.getDeliveredOn().toString());
        }

        dto.setProductNames(order.getProducts().stream()
                .map(Product::getName)
                .collect(Collectors.toList()));

        return dto;
    }
}
