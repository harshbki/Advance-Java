package com.shopsphere.dao;

import com.shopsphere.model.Order;
import java.sql.*;
import java.util.*;

public class OrderDAO {
    private final Connection connection;
    public OrderDAO(Connection connection) { this.connection=connection; }
    public int create(Order o) throws SQLException {
        String sql="INSERT INTO orders(user_id,address_id,total_amount,discount,payment_method,payment_status,order_status) VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,o.getUserId());ps.setInt(2,o.getAddressId());ps.setBigDecimal(3,o.getTotalAmount());ps.setBigDecimal(4,o.getDiscount());
            ps.setString(5,o.getPaymentMethod());ps.setString(6,o.getPaymentStatus());ps.setString(7,o.getOrderStatus());ps.executeUpdate();
            try(ResultSet keys=ps.getGeneratedKeys()){if(keys.next())return keys.getInt(1);}
        } return 0;
    }
    public List<Order> findByUser(int userId) throws SQLException {
        List<Order> out=new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement("SELECT * FROM orders WHERE user_id=? ORDER BY created_at DESC")){ps.setInt(1,userId);try(ResultSet rs=ps.executeQuery()){
            while(rs.next()){Order o=new Order();o.setOrderId(rs.getInt("order_id"));o.setUserId(rs.getInt("user_id"));o.setAddressId(rs.getInt("address_id"));o.setTotalAmount(rs.getBigDecimal("total_amount"));o.setDiscount(rs.getBigDecimal("discount"));o.setPaymentMethod(rs.getString("payment_method"));o.setPaymentStatus(rs.getString("payment_status"));o.setOrderStatus(rs.getString("order_status"));Timestamp t=rs.getTimestamp("created_at");if(t!=null)o.setCreatedAt(t.toLocalDateTime());out.add(o);}
        }} return out;
    }
    public List<Order> findAll() throws SQLException {
        List<Order> out=new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement("SELECT * FROM orders ORDER BY created_at DESC");ResultSet rs=ps.executeQuery()){
            while(rs.next()){Order o=new Order();o.setOrderId(rs.getInt("order_id"));o.setUserId(rs.getInt("user_id"));o.setAddressId(rs.getInt("address_id"));o.setTotalAmount(rs.getBigDecimal("total_amount"));o.setDiscount(rs.getBigDecimal("discount"));o.setPaymentMethod(rs.getString("payment_method"));o.setPaymentStatus(rs.getString("payment_status"));o.setOrderStatus(rs.getString("order_status"));Timestamp t=rs.getTimestamp("created_at");if(t!=null)o.setCreatedAt(t.toLocalDateTime());out.add(o);}
        }return out;
    }
    public Optional<Order> findByIdForUser(int orderId, int userId) throws SQLException {
        String sql="SELECT * FROM orders WHERE order_id=? AND user_id=?";
        try(PreparedStatement ps=connection.prepareStatement(sql)){
            ps.setInt(1,orderId);ps.setInt(2,userId);try(ResultSet rs=ps.executeQuery()){
                if(!rs.next())return Optional.empty();Order o=new Order();o.setOrderId(rs.getInt("order_id"));o.setUserId(rs.getInt("user_id"));o.setAddressId(rs.getInt("address_id"));o.setTotalAmount(rs.getBigDecimal("total_amount"));o.setDiscount(rs.getBigDecimal("discount"));o.setPaymentMethod(rs.getString("payment_method"));o.setPaymentStatus(rs.getString("payment_status"));o.setOrderStatus(rs.getString("order_status"));Timestamp t=rs.getTimestamp("created_at");if(t!=null)o.setCreatedAt(t.toLocalDateTime());return Optional.of(o);
            }
        }
    }
    public boolean updateStatus(int orderId,String status) throws SQLException {
        try(PreparedStatement ps=connection.prepareStatement("UPDATE orders SET order_status=? WHERE order_id=?")){ps.setString(1,status);ps.setInt(2,orderId);return ps.executeUpdate()==1;}
    }
}
