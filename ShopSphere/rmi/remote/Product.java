package com.shopsphere.rmi.remote;

import java.io.Serializable;import java.math.BigDecimal;

public class Product implements Serializable {
    private static final long serialVersionUID=1L;
    private final int productId;private final String name;private final BigDecimal price;private final int stock;
    public Product(int productId,String name,BigDecimal price,int stock){this.productId=productId;this.name=name;this.price=price;this.stock=stock;}
    public int getProductId(){return productId;}public String getName(){return name;}public BigDecimal getPrice(){return price;}public int getStock(){return stock;}
    @Override public String toString(){return "Product{id="+productId+", name='"+name+"', price="+price+", stock="+stock+"}";}
}
