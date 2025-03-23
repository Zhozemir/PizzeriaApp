package com.example.pizzeria.dto;

import com.example.pizzeria.enumerators.UserRole;

public class UserDTO {
    private Long id;
    private String username;
    private UserRole role;
    private String name;
    private String phone;

    public UserDTO() {}

    public UserDTO(Long id, String username, UserRole role, String name, String phone) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.name = name;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


