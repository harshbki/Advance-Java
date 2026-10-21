package com.shopsphere.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private int productId;
    private int categoryId;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private BigDecimal discount;
    private int stock;
    private String imageUrl;
    private boolean status;
    private LocalDateTime createdAt;

    public Product() { }
    public Product(int productId, int categoryId, String name, String brand, String description,
                   BigDecimal price, BigDecimal discount, int stock, String imageUrl,
                   boolean status, LocalDateTime createdAt) {
        this.productId = productId; this.categoryId = categoryId; this.name = name;
        this.brand = brand; this.description = description; this.price = price;
        this.discount = discount; this.stock = stock; this.imageUrl = imageUrl;
        this.status = status; this.createdAt = createdAt;
    }
    public int getProductId() { return productId; }
    public void setProductId(int value) { this.productId = value; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int value) { this.categoryId = value; }
    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
    public String getBrand() { return brand; }
    public void setBrand(String value) { this.brand = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal value) { this.price = value; }
    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal value) { this.discount = value; }
    public int getStock() { return stock; }
    public void setStock(int value) { this.stock = value; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String value) { this.imageUrl = value; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean value) { this.status = value; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime value) { this.createdAt = value; }
}
