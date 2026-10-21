package com.shopsphere.model;

import java.time.LocalDateTime;

public class WishlistItem {
    private int wishlistId;
    private int userId;
    private int productId;
    private LocalDateTime createdAt;

    public WishlistItem() { }
    public WishlistItem(int wishlistId, int userId, int productId, LocalDateTime createdAt) {
        this.wishlistId = wishlistId; this.userId = userId;
        this.productId = productId; this.createdAt = createdAt;
    }
    public int getWishlistId() { return wishlistId; }
    public void setWishlistId(int value) { this.wishlistId = value; }
    public int getUserId() { return userId; }
    public void setUserId(int value) { this.userId = value; }
    public int getProductId() { return productId; }
    public void setProductId(int value) { this.productId = value; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime value) { this.createdAt = value; }
}
