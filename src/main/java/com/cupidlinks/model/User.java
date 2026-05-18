package com.cupidlinks.model;

import java.time.LocalDateTime;

/**
 * Model class representing an application user account.
 */
public class User {

    private int userId;
    private String email;
    private String phone;
    private String passwordHash;
    private String role;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Creates an empty User object.
     */
    public User() {}

    /**
     * @return unique user ID
     */
    public int getUserId() { return userId; }

    /**
     * @param userId unique user ID
     */
    public void setUserId(int userId) { this.userId = userId; }

    /**
     * @return user's email address
     */
    public String getEmail() { return email; }

    /**
     * @param email user's email address
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * @return user's phone number
     */
    public String getPhone() { return phone; }

    /**
     * @param phone user's phone number
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * @return user's hashed password
     */
    public String getPasswordHash() { return passwordHash; }

    /**
     * @param passwordHash user's hashed password
     */
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    /**
     * @return user's application role
     */
    public String getRole() { return role; }

    /**
     * @param role user's application role
     */
    public void setRole(String role) { this.role = role; }

    /**
     * @return user's account status
     */
    public String getStatus() { return status; }

    /**
     * @param status user's account status
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * @return date and time when the account was created
     */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * @param createdAt date and time when the account was created
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /**
     * @return date and time when the account was last updated
     */
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    /**
     * @param updatedAt date and time when the account was last updated
     */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
