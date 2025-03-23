package com.example.pizzeria.controllers.validators;

import com.example.pizzeria.controllers.requests.OrderCreateRequest;
import com.example.pizzeria.enumerators.OrderStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class OrderControllerValidatorTest {

    private final OrderControllerValidator validator = new OrderControllerValidator();

    @Test
    void testValidateCreateOrderWithInvalidData() {

        OrderCreateRequest request = new OrderCreateRequest(new ArrayList<>());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateCreateOrder(request);
        });

        assertTrue(exception.getMessage().contains("Invalid OrderCreate request"));
    }

    @Test
    void testValidateCreateOrderWithValidData() {

        OrderCreateRequest request = new OrderCreateRequest(Arrays.asList(1L, 2L));
        assertDoesNotThrow(() -> validator.validateCreateOrder(request));

    }

    @Test
    void testValidateUpdateOrderStatusWithInvalidData() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateUpdateOrderStatus(null, OrderStatus.DELIVERED);
        });

        assertEquals("Грешни данни за обновяване на статус.", exception.getMessage());
    }

    @Test
    void testValidateUpdateOrderStatusWithValidData() {
        assertDoesNotThrow(() -> validator.validateUpdateOrderStatus(1L, OrderStatus.DELIVERED));
    }

    @Test
    void testValidateRepeatOrderWithInvalidData() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateRepeatOrder(null);
        });

        assertEquals("Невалидни данни за повтрорение на поръчката.", exception.getMessage());
    }

    @Test
    void testValidateRepeatOrderWithValidData() {
        assertDoesNotThrow(() -> validator.validateRepeatOrder(1L));
    }

}
