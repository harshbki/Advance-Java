package com.shopsphere.dao;

import com.shopsphere.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {
    private final Connection connection;
    public ProductDAO(Connection connection) { this.connection = connection; }

    public int create(Product p) throws SQLException {
        String sql = "INSERT INTO products (category_id,name,brand,description,price,discount,stock,image_url,status) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bind(ps, p); ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) return keys.getInt(1); }
        }
        return 0;
    }

    public Optional<Product> findById(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE product_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(map(rs)) : Optional.empty(); }
        }
    }

    public List<Product> findActive() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE status='ACTIVE' ORDER BY product_id");
             ResultSet rs = ps.executeQuery()) { while (rs.next()) products.add(map(rs)); }
        return products;
    }

    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM products ORDER BY product_id");
             ResultSet rs = ps.executeQuery()) { while (rs.next()) products.add(map(rs)); }
        return products;
    }

    public List<Product> findLowStock(int threshold) throws SQLException {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE stock <= ? ORDER BY stock ASC")) {
            ps.setInt(1, threshold);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) products.add(map(rs)); }
        }
        return products;
    }

    public List<Product> search(String text, Integer categoryId, String sort) throws SQLException {
        String orderBy = switch (sort == null ? "newest" : sort) {
            case "price-asc" -> "price ASC";
            case "price-desc" -> "price DESC";
            case "name" -> "name ASC";
            default -> "created_at DESC";
        };
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE status='ACTIVE'");
        if (text != null && !text.isBlank()) sql.append(" AND (name LIKE ? OR brand LIKE ? OR description LIKE ?)");
        if (categoryId != null && categoryId > 0) sql.append(" AND category_id=?");
        sql.append(" ORDER BY ").append(orderBy);
        List<Product> results = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            if (text != null && !text.isBlank()) {
                String pattern = "%" + text.trim() + "%";
                ps.setString(index++, pattern); ps.setString(index++, pattern); ps.setString(index++, pattern);
            }
            if (categoryId != null && categoryId > 0) ps.setInt(index, categoryId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) results.add(map(rs)); }
        }
        return results;
    }

    public boolean update(Product p) throws SQLException {
        String sql = "UPDATE products SET category_id=?,name=?,brand=?,description=?,price=?,discount=?,stock=?,image_url=?,status=? WHERE product_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            bind(ps, p); ps.setInt(10, p.getProductId()); return ps.executeUpdate() == 1;
        }
    }

    public boolean decrementStock(int productId, int quantity) throws SQLException {
        String sql = "UPDATE products SET stock=stock-? WHERE product_id=? AND status='ACTIVE' AND stock>=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity); ps.setInt(2, productId); ps.setInt(3, quantity);
            return ps.executeUpdate() == 1;
        }
    }
    public boolean delete(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM products WHERE product_id=?")) {
            ps.setInt(1, id); return ps.executeUpdate() == 1;
        }
    }

    private void bind(PreparedStatement ps, Product p) throws SQLException {
        ps.setInt(1, p.getCategoryId()); ps.setString(2, p.getName()); ps.setString(3, p.getBrand());
        ps.setString(4, p.getDescription()); ps.setBigDecimal(5, p.getPrice()); ps.setBigDecimal(6, p.getDiscount());
        ps.setInt(7, p.getStock()); ps.setString(8, p.getImageUrl()); ps.setString(9, p.isStatus() ? "ACTIVE" : "INACTIVE");
    }

    private Product map(ResultSet rs) throws SQLException {
        Product p = new Product(); p.setProductId(rs.getInt("product_id")); p.setCategoryId(rs.getInt("category_id"));
        p.setName(rs.getString("name")); p.setBrand(rs.getString("brand")); p.setDescription(rs.getString("description"));
        p.setPrice(rs.getBigDecimal("price")); p.setDiscount(rs.getBigDecimal("discount")); p.setStock(rs.getInt("stock"));
        p.setImageUrl(rs.getString("image_url")); p.setStatus("ACTIVE".equals(rs.getString("status")));
        Timestamp created = rs.getTimestamp("created_at"); if (created != null) p.setCreatedAt(created.toLocalDateTime());
        return p;
    }
}

