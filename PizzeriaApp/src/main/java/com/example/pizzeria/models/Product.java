package com.example.pizzeria.models;

import java.math.BigDecimal;

public class Product {

    private Long id;
    private String name;
    private BigDecimal price;
    private boolean isActive;

    public Product() {
        this.isActive = true;
    }

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return id + ". " + name + " - " + price + " лв" + (isActive ? "" : " (неактивен)");
    }
}

