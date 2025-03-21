package com.example.PizzeriaApp.models;

import com.example.PizzeriaApp.enumerators.UserRole;

public class User {

    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private String name;
    private String phone;

    public User() {}

    public User(String username, String password, UserRole role, String name, String phone) {

        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phone = phone;

    }

    public User(Long id, String username, UserRole role, String name, String phone) {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}
