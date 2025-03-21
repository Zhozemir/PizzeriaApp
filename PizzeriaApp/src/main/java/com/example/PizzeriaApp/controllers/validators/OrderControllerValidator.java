package com.example.PizzeriaApp.controllers.validators;


import com.example.PizzeriaApp.controllers.requests.OrderCreateRequest;
import com.example.PizzeriaApp.enumerators.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderControllerValidator {


    public boolean validateCreateOrder(OrderCreateRequest orderCreateRequest){

        if(orderCreateRequest == null || orderCreateRequest.getProductIds() == null || orderCreateRequest.getProductIds().isEmpty())
            return false;

        return true;
    }

    public boolean validateUpdateOrderStatus(Long id, OrderStatus status){

        if(id == null)
            return false;

        if(status == null || !( (status.toString().toUpperCase().equals("IN_PROGRESS")) || (status.toString().toUpperCase().equals("DELIVERED")) || (status.toString().toUpperCase().equals("CANCELLED")) ) )
            return false;

        return true;

    }

    public boolean validateRepeatOrder(Long id){

        if(id == null)
            return false;

        return true;

    }

}

