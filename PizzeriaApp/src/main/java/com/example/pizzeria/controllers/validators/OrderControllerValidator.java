package com.example.pizzeria.controllers.validators;

import com.example.pizzeria.controllers.requests.OrderCreateRequest;
import com.example.pizzeria.enumerators.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderControllerValidator {


    public void validateCreateOrder(OrderCreateRequest orderCreateRequest){

        if(orderCreateRequest == null || orderCreateRequest.getProductIds() == null || orderCreateRequest.getProductIds().isEmpty())
            throw new IllegalArgumentException(String.format("Invalid OrderCreate request [orderCreateRequest: %s]",
                    orderCreateRequest));
    }

    public void validateUpdateOrderStatus(Long id, OrderStatus status){

        if(id == null || status == null || !( (status.toString().toUpperCase().equals("IN_PROGRESS")) || (status.toString().toUpperCase().equals("DELIVERED")) || (status.toString().toUpperCase().equals("CANCELLED")) ) )
             throw new IllegalArgumentException("Грешни данни за обновяване на статус.");


        // да направя updateOrderRequest... и може би вс останали

    }

    public void validateRepeatOrder(Long id){

        if(id == null)
            throw new IllegalArgumentException("Невалидни данни за повтрорение на поръчката.");

    }

}

