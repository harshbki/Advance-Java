package com.shopsphere.swing.model;

import java.time.LocalDateTime;

public class User {
    private int userId;
    private String name;
    private String email;
    private String mobile;
    private String role;
    private boolean status;
    private LocalDateTime createdAt;

    public User() { }
    public User(int userId, String name, String email, String mobile, String role, boolean status, LocalDateTime createdAt) {
        this.userId = userId; this.name = name; this.email = email;
        this.mobile = mobile; this.role = role; this.status = status; this.createdAt = createdAt;
    }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
