package com.example.pizzeria.controllers;

import com.example.pizzeria.controllers.requests.OrderCreateRequest;
import com.example.pizzeria.services.interfaces.OrderService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@Autowired
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testCreateOrderWithInvalidData() throws Exception{

        OrderCreateRequest request = new OrderCreateRequest(new ArrayList<>());

        mockMvc.perform(post("/api/orders/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Невалидни данни."));

    }

    @Test
    void testCreateOrderSuccess() throws Exception{

        OrderCreateRequest request = new OrderCreateRequest(new ArrayList<>());

        mockMvc.perform(post("/api/orders/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Поръчката е създадена. Очаквано време за доставка: ~30 минути."));

    }

    @Test
    void testUpdateOrderStatusWithInvalidData() throws Exception{

        mockMvc.perform(put("/api/orders/{id}/status", 1)
                        .param("status", "invalidStatus"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Обща грешка."));

    }

    @Test
    void testUpdateOrderStatusSuccess() throws Exception{

        mockMvc.perform(put("/api/orders/{id}/status", 1)
                        .param("status", "DELIVERED"))
                .andExpect(status().isOk())
                .andExpect(content().string("Статусът на поръчката е обновен."));

    }

    @Test
    void testRepeatOrderWithInvalidData() throws Exception{

        mockMvc.perform(post("/api/orders/{id}/repeat", 99))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Обща грешка."));

    }

    @Test
    void testRepeatOrderSuccess() throws Exception{

        mockMvc.perform(post("/api/orders/{id}/repeat", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Поръчката е повторена. Очаквано време за доставка: ~30 минути."));

    }

}
