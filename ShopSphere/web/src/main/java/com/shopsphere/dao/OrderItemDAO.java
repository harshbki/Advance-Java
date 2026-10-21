package com.shopsphere.dao;

import com.shopsphere.model.OrderItem;
import java.sql.*;
import java.util.*;

public class OrderItemDAO {
    private final Connection connection;
    public OrderItemDAO(Connection connection){this.connection=connection;}
    public int create(OrderItem item) throws SQLException {
        String sql="INSERT INTO order_items(order_id,product_id,quantity,price) VALUES(?,?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,item.getOrderId());ps.setInt(2,item.getProductId());ps.setInt(3,item.getQuantity());ps.setBigDecimal(4,item.getPrice());ps.executeUpdate();
            try(ResultSet keys=ps.getGeneratedKeys()){if(keys.next())return keys.getInt(1);}
        }return 0;
    }
    public List<OrderItem> findByOrder(int orderId) throws SQLException {
        List<OrderItem> out=new ArrayList<>();
        String sql = "SELECT oi.*, COALESCE(p.name, CONCAT('Product #', oi.product_id)) AS product_name, " +
                     "p.image_url, p.brand " +
                     "FROM order_items oi LEFT JOIN products p ON oi.product_id = p.product_id WHERE oi.order_id=?";
        try(PreparedStatement ps=connection.prepareStatement(sql)){ps.setInt(1,orderId);try(ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                OrderItem i=new OrderItem();
                i.setOrderItemId(rs.getInt("order_item_id"));
                i.setOrderId(rs.getInt("order_id"));
                i.setProductId(rs.getInt("product_id"));
                i.setQuantity(rs.getInt("quantity"));
                i.setPrice(rs.getBigDecimal("price"));
                i.setProductName(rs.getString("product_name"));
                i.setProductImage(rs.getString("image_url"));
                i.setBrand(rs.getString("brand"));
                out.add(i);
            }
        }}return out;
    }
}
