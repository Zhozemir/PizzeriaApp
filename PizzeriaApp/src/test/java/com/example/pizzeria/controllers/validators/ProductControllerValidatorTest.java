package com.example.pizzeria.controllers.validators;

import com.example.pizzeria.controllers.requests.ProductCreateRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerValidatorTest {

    private final ProductControllerValidator validator = new ProductControllerValidator();

    @Test
    void testCreateProductValidatorWithInvalidData() {

        ProductCreateRequest request = new ProductCreateRequest("", BigDecimal.ZERO);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.createProductValidator(request);
        });

        assertTrue(exception.getMessage().contains("Invalid ProductCreate request"));
    }

    @Test
    void testCreateProductValidatorWithValidData() {

        ProductCreateRequest request = new ProductCreateRequest("Pizza", BigDecimal.valueOf(5));
        assertDoesNotThrow(() -> validator.createProductValidator(request));

    }

    @Test
    void testValidateDeactivateProductWithInvalidData() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validateDeactivateProduct(null);
        });

        assertEquals("Невалидни данни за деактивиране на продукта.", exception.getMessage());
    }

    @Test
    void testValidateDeactivateProductWithValidData() {
        assertDoesNotThrow(() -> validator.validateDeactivateProduct(1L));
    }

}
