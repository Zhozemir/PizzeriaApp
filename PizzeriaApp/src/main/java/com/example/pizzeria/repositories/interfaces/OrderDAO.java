package com.example.pizzeria.repositories.interfaces;

import com.example.pizzeria.enumerators.OrderStatus;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.User;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    boolean save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findByStatus(OrderStatus status);

    Optional<User> getUserById(Long userId, Connection conn);

    List<Order> findAll();
    boolean updateStatus(Long orderId, OrderStatus newStatus);
    List<Order> findByCreatedOnBetween(LocalDateTime start, LocalDateTime end);

    List<Order> findByUserId(Long id);

}
