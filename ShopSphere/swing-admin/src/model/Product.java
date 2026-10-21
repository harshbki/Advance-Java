package com.shopsphere.swing.model;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private int categoryId;
    private String name;
    private BigDecimal price;
    private int stock;
    public Product() { }
    public Product(int productId,int categoryId,String name,BigDecimal price,int stock){this.productId=productId;this.categoryId=categoryId;this.name=name;this.price=price;this.stock=stock;}
    public int getProductId(){return productId;} public void setProductId(int v){productId=v;}
    public int getCategoryId(){return categoryId;} public void setCategoryId(int v){categoryId=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
    public int getStock(){return stock;} public void setStock(int v){stock=v;}
}
