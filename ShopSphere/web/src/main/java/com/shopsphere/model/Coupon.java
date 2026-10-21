package com.shopsphere.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Coupon {
    private int couponId;
    private String code;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal minimumOrder;
    private BigDecimal maximumDiscount;
    private LocalDateTime expiryDate;
    private String status;

    public Coupon() { }
    public int getCouponId() { return couponId; }
    public void setCouponId(int value) { this.couponId = value; }
    public String getCode() { return code; }
    public void setCode(String value) { this.code = value; }
    public String getDiscountType() { return discountType; }
    public void setDiscountType(String value) { this.discountType = value; }
    public BigDecimal getDiscountValue() { return discountValue; }
    public void setDiscountValue(BigDecimal value) { this.discountValue = value; }
    public BigDecimal getMinimumOrder() { return minimumOrder; }
    public void setMinimumOrder(BigDecimal value) { this.minimumOrder = value; }
    public BigDecimal getMaximumDiscount() { return maximumDiscount; }
    public void setMaximumDiscount(BigDecimal value) { this.maximumDiscount = value; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime value) { this.expiryDate = value; }
    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }
}
