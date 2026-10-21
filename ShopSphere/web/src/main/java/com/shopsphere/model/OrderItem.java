package com.shopsphere.model;

import java.math.BigDecimal;

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal price;
    private String productName;
    private String productImage;
    private String brand;

    public OrderItem() { }
    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int value) { this.orderItemId = value; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int value) { this.orderId = value; }
    public int getProductId() { return productId; }
    public void setProductId(int value) { this.productId = value; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int value) { this.quantity = value; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal value) { this.price = value; }
    public String getProductName() { return productName; }
    public void setProductName(String value) { this.productName = value; }
    public String getProductImage() { return productImage; }
    public void setProductImage(String value) { this.productImage = value; }
    public String getBrand() { return brand; }
    public void setBrand(String value) { this.brand = value; }
}
