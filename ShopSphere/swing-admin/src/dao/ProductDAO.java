package com.shopsphere.swing.dao;

import com.shopsphere.swing.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> findAll()throws SQLException{
        List<Product> products=new ArrayList<>();try(Connection c=Database.open();PreparedStatement ps=c.prepareStatement("SELECT product_id,category_id,name,price,stock FROM products ORDER BY product_id");ResultSet rs=ps.executeQuery()){
            while(rs.next())products.add(new Product(rs.getInt("product_id"),rs.getInt("category_id"),rs.getString("name"),rs.getBigDecimal("price"),rs.getInt("stock")));
        }return products;
    }
    public int add(Product product)throws SQLException{
        try(Connection c=Database.open();PreparedStatement ps=c.prepareStatement("INSERT INTO products(category_id,name,price,discount,stock,status) VALUES(?,?,?,0,?,'ACTIVE')")){
            ps.setInt(1,product.getCategoryId());ps.setString(2,product.getName());ps.setBigDecimal(3,product.getPrice());ps.setInt(4,product.getStock());return ps.executeUpdate();
        }
    }
    public int delete(int productId)throws SQLException{
        try(Connection c=Database.open();PreparedStatement ps=c.prepareStatement("DELETE FROM products WHERE product_id=?")){ps.setInt(1,productId);return ps.executeUpdate();}
    }
    public int update(Product product)throws SQLException{
        try(Connection c=Database.open();PreparedStatement ps=c.prepareStatement("UPDATE products SET category_id=?, name=?, price=?, stock=? WHERE product_id=?")){
            ps.setInt(1,product.getCategoryId());ps.setString(2,product.getName());ps.setBigDecimal(3,product.getPrice());ps.setInt(4,product.getStock());ps.setInt(5,product.getProductId());
            return ps.executeUpdate();
        }
    }
}
