package com.example.pizzeria.controllers;

import com.example.pizzeria.controllers.requests.ProductCreateRequest;
import com.example.pizzeria.services.interfaces.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void testAddProductWithInvalidData() throws Exception{

        ProductCreateRequest request = new ProductCreateRequest("", BigDecimal.ZERO);

        mockMvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Невалидни данни."));

    }

    @Test
    void testAddProductSuccess() throws Exception{

        ProductCreateRequest request = new ProductCreateRequest("Pizza", BigDecimal.ONE);

        mockMvc.perform(post("/api/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Продуктът е добавен"));


    }

    @Test
    void testDeactivateProductWithInvalidData() throws Exception{

        mockMvc.perform(put("/api/products/{id}/deactivate", -1))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Невалидни данни."));

    }

    @Test
    void testDeactivateProductSuccess() throws Exception{

        mockMvc.perform(put("/api/products/{id}/deactivate", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Продуктът е деактивиран"));

    }

}
