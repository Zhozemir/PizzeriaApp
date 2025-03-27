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

    }

    public void validateRepeatOrder(Long id){

        if(id == null || id <= 0)
            throw new IllegalArgumentException("Невалидни данни за повтрорение на поръчката.");

    }

}

