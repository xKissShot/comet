package com.fmi.comet.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {

    private Long id;  // Changed to Long to match BIGINT in database
    private String username;
    private String password;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;  // Timestamp for soft deletion
    private Role role;
    private List<User> friends = new ArrayList<>();

    public enum Role {
        ADMIN,
        OWNER,
        GUEST
    }

    public User() {}

    public User(Long id, String username, String password, Boolean isDeleted, LocalDateTime deletedAt, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.role = role;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isDeleted=" + isDeleted +
                ", deletedAt=" + deletedAt +
                ", role=" + role +
                '}';
    }
}
