package com.example.PizzeriaApp.services.interfaces;

import com.example.PizzeriaApp.enumerators.OrderStatus;
import com.example.PizzeriaApp.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    boolean createOrder(List<Long> productIds);
    List<Order> getOrdersByStatus(OrderStatus status);
    List<Order> getAllOrders();
    boolean updateOrderStatus(Long orderId, OrderStatus newStatus);
    List<Order> getOrdersByPeriod(LocalDateTime start, LocalDateTime end);
    boolean repeatOrder(Long orderId);

    List<Order> getOrdersByUser();
}
