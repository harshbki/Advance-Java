package com.shopsphere.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private int paymentId;
    private int orderId;
    private String paymentMethod;
    private String transactionReference;
    private BigDecimal amount;
    private String paymentStatus;
    private LocalDateTime createdAt;

    public Payment() { }
    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int value) { this.paymentId = value; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int value) { this.orderId = value; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String value) { this.paymentMethod = value; }
    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String value) { this.transactionReference = value; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal value) { this.amount = value; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String value) { this.paymentStatus = value; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime value) { this.createdAt = value; }
}
