package com.shopsphere.dao;

import com.shopsphere.model.Address;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {
    private final Connection connection;
    public AddressDAO(Connection connection) { this.connection = connection; }

    public boolean belongsToUser(int addressId, int userId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM addresses WHERE address_id=? AND user_id=?")) {
            ps.setInt(1, addressId); ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public List<Address> findByUser(int userId) throws SQLException {
        List<Address> addresses = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM addresses WHERE user_id=? ORDER BY address_id")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) addresses.add(new Address(rs.getInt("address_id"), rs.getInt("user_id"),
                        rs.getString("address_line"), rs.getString("city"), rs.getString("state"),
                        rs.getString("pincode"), rs.getString("address_type")));
            }
        }
        return addresses;
    }

    public java.util.Optional<Address> findById(int addressId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM addresses WHERE address_id=?")) {
            ps.setInt(1, addressId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return java.util.Optional.of(new Address(rs.getInt("address_id"), rs.getInt("user_id"),
                            rs.getString("address_line"), rs.getString("city"), rs.getString("state"),
                            rs.getString("pincode"), rs.getString("address_type")));
                }
                return java.util.Optional.empty();
            }
        }
    }

    public int create(Address address) throws SQLException {
        String sql = "INSERT INTO addresses(user_id,address_line,city,state,pincode,address_type) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, address.getUserId()); ps.setString(2, address.getAddressLine());
            ps.setString(3, address.getCity()); ps.setString(4, address.getState());
            ps.setString(5, address.getPincode()); ps.setString(6, address.getAddressType());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) return keys.getInt(1); }
        }
        return 0;
    }
}
