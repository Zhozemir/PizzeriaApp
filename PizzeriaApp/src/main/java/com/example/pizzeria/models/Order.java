package com.example.pizzeria.models;

import com.example.pizzeria.enumerators.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private Long id;
    private List<Product> products = new ArrayList<>();
    private OrderStatus status;
    private LocalDateTime createdOn;
    private LocalDateTime deliveredOn;

    private User user;

    public Order() {
        this.status = OrderStatus.IN_PROGRESS;
        this.createdOn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDeliveredOn(){
        return deliveredOn;
    }

    public void setDeliveredOn(LocalDateTime deliveredOn){
        this.deliveredOn = deliveredOn;
    }

    @Override
    public String toString() {
        return "Поръчка " + id + " - Статус: " + status + " | Продукти: " + products;
    }
}
