package com.example.ipfication.ipfication_service.dto;

public class UserResponse {
    private String sub;
    private String name;
    private String email;

    // Getters and Setters
    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
