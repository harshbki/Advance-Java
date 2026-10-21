package com.shopsphere.model;

public class CartItem {
    private int cartId;
    private int userId;
    private int productId;
    private int quantity;

    public CartItem() { }
    public CartItem(int cartId, int userId, int productId, int quantity) {
        this.cartId = cartId; this.userId = userId; this.productId = productId; this.quantity = quantity;
    }
    public int getCartId() { return cartId; }
    public void setCartId(int value) { this.cartId = value; }
    public int getUserId() { return userId; }
    public void setUserId(int value) { this.userId = value; }
    public int getProductId() { return productId; }
    public void setProductId(int value) { this.productId = value; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int value) { this.quantity = value; }
}
