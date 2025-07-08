package com.example.pizzeria.services.interfaces;

import com.example.pizzeria.enumerators.OrderStatus;
import com.example.pizzeria.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order createOrder(List<Long> productIds);
    List<Order> getOrdersByStatus(OrderStatus status);
    List<Order> getAllOrders();
    void updateOrderStatus(Long orderId, OrderStatus newStatus);
    List<Order> getOrdersByPeriod(LocalDateTime start, LocalDateTime end);
    Order repeatOrder(Long orderId);

    List<Order> getOrdersByUser();

    List<Order> getOrdersByUserId(Long userId);

    List<Order> getDeliveredAfter(LocalDateTime since);
}
