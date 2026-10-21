package com.shopsphere.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdminSummaryDAO {
    private final Connection connection;
    public AdminSummaryDAO(Connection connection) { this.connection = connection; }
    public Map<String, Object> load() throws SQLException {
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("products", count("SELECT COUNT(*) FROM products"));
        summary.put("customers", count("SELECT COUNT(*) FROM users WHERE role='CUSTOMER'"));
        summary.put("orders", count("SELECT COUNT(*) FROM orders"));
        summary.put("lowStock", count("SELECT COUNT(*) FROM products WHERE stock <= 5 AND status='ACTIVE'"));
        try (PreparedStatement ps = connection.prepareStatement("SELECT COALESCE(SUM(total_amount),0) FROM orders WHERE order_status <> 'CANCELLED'"); ResultSet rs = ps.executeQuery()) {
            summary.put("sales", rs.next() ? rs.getBigDecimal(1) : java.math.BigDecimal.ZERO);
        }
        return summary;
    }
    private long count(String sql) throws SQLException {
        try (PreparedStatement ps=connection.prepareStatement(sql); ResultSet rs=ps.executeQuery()) { return rs.next()?rs.getLong(1):0; }
    }
}
