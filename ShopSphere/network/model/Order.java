package com.shopsphere.network.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId;
    private int userId;
    private BigDecimal totalAmount;
    private String orderStatus;
    private String paymentStatus;

    public Order(int orderId, int userId, BigDecimal totalAmount, String orderStatus, String paymentStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
    }

    public int getOrderId() { return orderId; }
    public int getUserId() { return userId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getOrderStatus() { return orderStatus; }
    public String getPaymentStatus() { return paymentStatus; }

    @Override
    public String toString() {
        return "Order{id=" + orderId + ", userId=" + userId + ", total=" + totalAmount + ", orderStatus='" + orderStatus + "', paymentStatus='" + paymentStatus + "'}";
    }
}
