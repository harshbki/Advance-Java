package com.shopsphere.dao;

import com.shopsphere.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final Connection connection;
    public UserDAO(Connection connection) { this.connection = connection; }

    public int create(User user) throws SQLException {
        String sql = "INSERT INTO users (name,email,password,mobile,role,status) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName()); ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword()); ps.setString(4, user.getMobile());
            ps.setString(5, user.getRole()); ps.setString(6, user.isStatus() ? "ACTIVE" : "DISABLED");
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) return keys.getInt(1); }
        }
        return 0;
    }

    public Optional<User> findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(map(rs)) : Optional.empty(); }
        }
    }

    public Optional<User> findById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(map(rs)) : Optional.empty(); }
        }
    }

    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET name=?, email=?, password=?, mobile=?, role=?, status=? WHERE user_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName()); ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword()); ps.setString(4, user.getMobile());
            ps.setString(5, user.getRole()); ps.setString(6, user.isStatus() ? "ACTIVE" : "DISABLED");
            ps.setInt(7, user.getUserId()); return ps.executeUpdate() == 1;
        }
    }

    public List<User> findAllCustomers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE role='CUSTOMER' ORDER BY created_at DESC");
             ResultSet rs = ps.executeQuery()) { while (rs.next()) users.add(map(rs)); }
        return users;
    }

    private User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id")); user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email")); user.setPassword(rs.getString("password"));
        user.setMobile(rs.getString("mobile")); user.setRole(rs.getString("role"));
        user.setStatus("ACTIVE".equals(rs.getString("status")));
        Timestamp created = rs.getTimestamp("created_at");
        if (created != null) user.setCreatedAt(created.toLocalDateTime());
        return user;
    }
}
