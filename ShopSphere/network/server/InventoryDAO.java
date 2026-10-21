package com.shopsphere.network.server;

import com.shopsphere.network.model.Product;
import java.sql.*;import java.util.ArrayList;import java.util.List;

final class InventoryDAO {
    Product findProduct(int id)throws SQLException{
        try(Connection c=DatabaseConnection.open();PreparedStatement ps=c.prepareStatement("SELECT product_id,name,price,stock FROM products WHERE product_id=? AND status='ACTIVE'")){
            ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){return rs.next()?new Product(rs.getInt(1),rs.getString(2),rs.getBigDecimal(3),rs.getInt(4)):null;}
        }
    }
    int getStock(int id)throws SQLException{
        try(Connection c=DatabaseConnection.open();PreparedStatement ps=c.prepareStatement("SELECT stock FROM products WHERE product_id=? AND status='ACTIVE'")){
            ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){return rs.next()?rs.getInt(1):-1;}
        }
    }
    List<Product> search(String query)throws SQLException{
        List<Product> results=new ArrayList<>();try(Connection c=DatabaseConnection.open();PreparedStatement ps=c.prepareStatement("SELECT product_id,name,price,stock FROM products WHERE status='ACTIVE' AND name LIKE ? ORDER BY name LIMIT 20")){
            ps.setString(1,"%"+query+"%");try(ResultSet rs=ps.executeQuery()){while(rs.next())results.add(new Product(rs.getInt(1),rs.getString(2),rs.getBigDecimal(3),rs.getInt(4)));}
        }return results;
    }
    com.shopsphere.network.model.Order findOrder(int id)throws SQLException{
        try(Connection c=DatabaseConnection.open();PreparedStatement ps=c.prepareStatement("SELECT order_id,user_id,total_amount,order_status,payment_status FROM orders WHERE order_id=?")){
            ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){return rs.next()?new com.shopsphere.network.model.Order(rs.getInt(1),rs.getInt(2),rs.getBigDecimal(3),rs.getString(4),rs.getString(5)):null;}
        }
    }
}
