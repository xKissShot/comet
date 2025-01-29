package com.fmi.comet.model;

public class Channel {
    private Long id;
    private String name;
    private Long ownerId;
    private Boolean isDeleted;

    // Getters and Setters
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    // Rename this method to getIsDeleted()
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Channel{id=" + id + ", name='" + name + "', ownerId=" + ownerId + ", isDeleted=" + isDeleted + "}";
    }
}
