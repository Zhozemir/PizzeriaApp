package com.example.pizzeria.controllers.requests;

import com.example.pizzeria.enumerators.UserRole;

public class UserRegisterRequest {

    private String username;
    private String password;
    private UserRole role;
    private String name;
    private String phone;

    public UserRegisterRequest(String username, String password, UserRole role, String name, String phone) {

        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phone = phone;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
