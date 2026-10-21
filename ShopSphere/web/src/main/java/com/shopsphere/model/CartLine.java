package com.shopsphere.model;

import java.math.BigDecimal;

/** Joined cart row used to render product details alongside the stored cart quantity. */
public class CartLine {
    private final int cartId;
    private final Product product;
    private final int quantity;

    public CartLine(int cartId, Product product, int quantity) {
        this.cartId = cartId; this.product = product; this.quantity = quantity;
    }
    public int getCartId() { return cartId; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public BigDecimal getLineTotal() {
        BigDecimal unit = product.getPrice().multiply(BigDecimal.ONE.subtract(product.getDiscount().movePointLeft(2)));
        return unit.multiply(BigDecimal.valueOf(quantity)).setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
