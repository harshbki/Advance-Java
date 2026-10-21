package com.shopsphere.dao;

import com.shopsphere.model.Payment;
import java.sql.*;

public class PaymentDAO {
    private final Connection connection;
    public PaymentDAO(Connection connection){this.connection=connection;}
    public int create(Payment p) throws SQLException {
        String sql="INSERT INTO payments(order_id,payment_method,transaction_reference,amount,payment_status) VALUES(?,?,?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,p.getOrderId());ps.setString(2,p.getPaymentMethod());ps.setString(3,p.getTransactionReference());ps.setBigDecimal(4,p.getAmount());ps.setString(5,p.getPaymentStatus());ps.executeUpdate();
            try(ResultSet keys=ps.getGeneratedKeys()){if(keys.next())return keys.getInt(1);}
        }return 0;
    }
    public boolean updateStatus(int paymentId,String status) throws SQLException {
        try(PreparedStatement ps=connection.prepareStatement("UPDATE payments SET payment_status=? WHERE payment_id=?")){ps.setString(1,status);ps.setInt(2,paymentId);return ps.executeUpdate()==1;}
    }

    public java.util.Optional<Payment> findByOrder(int orderId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM payments WHERE order_id=? ORDER BY payment_id DESC LIMIT 1")) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Payment p = new Payment();
                    p.setPaymentId(rs.getInt("payment_id"));
                    p.setOrderId(rs.getInt("order_id"));
                    p.setPaymentMethod(rs.getString("payment_method"));
                    p.setTransactionReference(rs.getString("transaction_reference"));
                    p.setAmount(rs.getBigDecimal("amount"));
                    p.setPaymentStatus(rs.getString("payment_status"));
                    return java.util.Optional.of(p);
                }
                return java.util.Optional.empty();
            }
        }
    }
}
