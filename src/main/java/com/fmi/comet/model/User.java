package com.fmi.comet.model;

import org.springframework.beans.factory.annotation.*;

public class User {

    private Integer id;
    private String username;
    private String password;
    private Boolean deleted;

    // Default constructor
    public User() {}

    // Constructor with all fields
    public User(Integer id, String username, String password, Boolean deleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.deleted = deleted;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', password='" + password + "', deleted=" + deleted + '}';
    }
}