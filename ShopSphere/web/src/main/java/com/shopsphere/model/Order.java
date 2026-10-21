package com.shopsphere.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private int userId;
    private int addressId;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private String paymentMethod;
    private String paymentStatus;
    private String orderStatus;
    private LocalDateTime createdAt;

    public Order() { }
    public int getOrderId() { return orderId; }
    public void setOrderId(int value) { this.orderId = value; }
    public int getUserId() { return userId; }
    public void setUserId(int value) { this.userId = value; }
    public int getAddressId() { return addressId; }
    public void setAddressId(int value) { this.addressId = value; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal value) { this.totalAmount = value; }
    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal value) { this.discount = value; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String value) { this.paymentMethod = value; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String value) { this.paymentStatus = value; }
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String value) { this.orderStatus = value; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime value) { this.createdAt = value; }
}
