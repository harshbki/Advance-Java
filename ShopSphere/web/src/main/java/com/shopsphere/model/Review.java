package com.shopsphere.model;

import java.time.LocalDateTime;

public class Review {
    private int reviewId;
    private int userId;
    private int productId;
    private int rating;
    private String reviewText;
    private LocalDateTime createdAt;

    public Review() { }
    public int getReviewId() { return reviewId; }
    public void setReviewId(int value) { this.reviewId = value; }
    public int getUserId() { return userId; }
    public void setUserId(int value) { this.userId = value; }
    public int getProductId() { return productId; }
    public void setProductId(int value) { this.productId = value; }
    public int getRating() { return rating; }
    public void setRating(int value) { this.rating = value; }
    public String getReviewText() { return reviewText; }
    public void setReviewText(String value) { this.reviewText = value; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime value) { this.createdAt = value; }
}
