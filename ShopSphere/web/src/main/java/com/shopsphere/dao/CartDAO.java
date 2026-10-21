package com.shopsphere.dao;

import com.shopsphere.model.CartItem;
import com.shopsphere.model.CartLine;
import com.shopsphere.model.Product;
import java.sql.*;
import java.util.*;

public class CartDAO {
    private final Connection connection;
    public CartDAO(Connection connection) { this.connection=connection; }
    public void addOrIncrease(int userId,int productId,int quantity) throws SQLException {
        String sql="INSERT INTO cart(user_id,product_id,quantity) VALUES(?,?,?) ON DUPLICATE KEY UPDATE quantity=quantity+VALUES(quantity)";
        try(PreparedStatement ps=connection.prepareStatement(sql)){ps.setInt(1,userId);ps.setInt(2,productId);ps.setInt(3,quantity);ps.executeUpdate();}
    }
    public List<CartItem> findByUser(int userId) throws SQLException {
        List<CartItem> out=new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement("SELECT * FROM cart WHERE user_id=?")){ps.setInt(1,userId);try(ResultSet rs=ps.executeQuery()){
            while(rs.next()){CartItem c=new CartItem(rs.getInt("cart_id"),rs.getInt("user_id"),rs.getInt("product_id"),rs.getInt("quantity"));out.add(c);}
        }} return out;
    }
    public List<CartLine> findLinesByUser(int userId) throws SQLException {
        List<CartLine> out = new ArrayList<>();
        String sql = "SELECT c.cart_id,c.quantity,p.* FROM cart c JOIN products p ON p.product_id=c.product_id WHERE c.user_id=? ORDER BY c.cart_id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product(); p.setProductId(rs.getInt("product_id")); p.setCategoryId(rs.getInt("category_id"));
                    p.setName(rs.getString("name")); p.setBrand(rs.getString("brand")); p.setDescription(rs.getString("description"));
                    p.setPrice(rs.getBigDecimal("price")); p.setDiscount(rs.getBigDecimal("discount")); p.setStock(rs.getInt("stock"));
                    p.setImageUrl(rs.getString("image_url")); p.setStatus("ACTIVE".equals(rs.getString("status")));
                    out.add(new CartLine(rs.getInt("cart_id"), p, rs.getInt("quantity")));
                }
            }
        }
        return out;
    }
    public int findQuantity(int userId, int productId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT quantity FROM cart WHERE user_id=? AND product_id=?")) {
            ps.setInt(1,userId); ps.setInt(2,productId);
            try (ResultSet rs=ps.executeQuery()) { return rs.next()?rs.getInt(1):0; }
        }
    }
    public int countItemsByUser(int userId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT COALESCE(SUM(quantity), 0) FROM cart WHERE user_id=?")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public boolean updateQuantity(int cartId,int userId,int quantity) throws SQLException {
        try(PreparedStatement ps=connection.prepareStatement("UPDATE cart SET quantity=? WHERE cart_id=? AND user_id=?")){ps.setInt(1,quantity);ps.setInt(2,cartId);ps.setInt(3,userId);return ps.executeUpdate()==1;}
    }
    public void clearForUser(int userId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM cart WHERE user_id=?")) {
            ps.setInt(1, userId); ps.executeUpdate();
        }
    }    public boolean remove(int cartId,int userId) throws SQLException {
        try(PreparedStatement ps=connection.prepareStatement("DELETE FROM cart WHERE cart_id=? AND user_id=?")){ps.setInt(1,cartId);ps.setInt(2,userId);return ps.executeUpdate()==1;}
    }
}

