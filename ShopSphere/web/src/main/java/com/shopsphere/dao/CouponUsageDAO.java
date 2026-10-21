package com.shopsphere.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CouponUsageDAO {
    private final Connection connection;
    public CouponUsageDAO(Connection connection) { this.connection = connection; }

    public boolean hasUserUsedCoupon(int couponId, int userId) throws SQLException {
        String sql = "SELECT 1 FROM coupon_usage WHERE coupon_id = ? AND user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, couponId);
            ps.setInt(2, userId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void record(int couponId, int userId, int orderId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO coupon_usage(coupon_id,user_id,order_id) VALUES(?,?,?)")) {
            ps.setInt(1, couponId);
            ps.setInt(2, userId);
            ps.setInt(3, orderId);
            ps.executeUpdate();
        }
    }
}
