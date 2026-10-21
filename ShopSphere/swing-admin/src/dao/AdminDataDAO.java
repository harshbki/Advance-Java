package com.shopsphere.swing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Data access used by the Swing administration screens. */
public class AdminDataDAO {
    public List<Object[]> categories() throws SQLException {
        return rows("SELECT category_id,name,description,status FROM categories ORDER BY name");
    }
    public void addCategory(String name, String description) throws SQLException {
        try (Connection c = Database.open(); PreparedStatement p = c.prepareStatement(
                "INSERT INTO categories(name,description,status) VALUES(?,?,'ACTIVE')")) {
            p.setString(1, name); p.setString(2, description); p.executeUpdate();
        }
    }
    public void deleteCategory(int id) throws SQLException { update("DELETE FROM categories WHERE category_id=?", id); }
    public void updateCategory(int id, String name, String description) throws SQLException {
        try (Connection c = Database.open(); PreparedStatement p = c.prepareStatement(
                "UPDATE categories SET name=?, description=? WHERE category_id=?")) {
            p.setString(1, name); p.setString(2, description); p.setInt(3, id); p.executeUpdate();
        }
    }

    public List<Object[]> customers() throws SQLException {
        return rows("SELECT user_id,name,email,mobile,status,created_at FROM users WHERE role='CUSTOMER' ORDER BY created_at DESC");
    }
    public List<Object[]> orders() throws SQLException {
        return rows("SELECT order_id,user_id,total_amount,payment_status,order_status,created_at FROM orders ORDER BY created_at DESC");
    }
    public void updateOrderStatus(int id, String status) throws SQLException {
        try (Connection c = Database.open(); PreparedStatement p = c.prepareStatement("UPDATE orders SET order_status=? WHERE order_id=?")) {
            p.setString(1, status); p.setInt(2, id); p.executeUpdate();
        }
    }
    public List<Object[]> lowStock() throws SQLException {
        return rows("SELECT product_id,name,stock FROM products WHERE status='ACTIVE' AND stock<=5 ORDER BY stock,name");
    }
    public void updateStock(int id, int stock) throws SQLException {
        try (Connection c = Database.open(); PreparedStatement p = c.prepareStatement("UPDATE products SET stock=? WHERE product_id=?")) {
            p.setInt(1, stock); p.setInt(2, id); p.executeUpdate();
        }
    }
    private void update(String sql, int id) throws SQLException {
        try (Connection c = Database.open(); PreparedStatement p = c.prepareStatement(sql)) { p.setInt(1, id); p.executeUpdate(); }
    }
    private List<Object[]> rows(String sql) throws SQLException {
        List<Object[]> result = new ArrayList<>();
        try (Connection c = Database.open(); PreparedStatement p = c.prepareStatement(sql); ResultSet r = p.executeQuery()) {
            int columns = r.getMetaData().getColumnCount();
            while (r.next()) { Object[] row = new Object[columns]; for (int i=0;i<columns;i++) row[i]=r.getObject(i+1); result.add(row); }
        }
        return result;
    }
}
