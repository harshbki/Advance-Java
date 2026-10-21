package com.shopsphere.dao;

import com.shopsphere.model.Coupon;
import java.sql.*;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class CouponDAO {
    private final Connection connection;
    public CouponDAO(Connection connection){this.connection=connection;}
    public Optional<Coupon> findActiveByCode(String code) throws SQLException {
        String sql="SELECT * FROM coupons WHERE code=? AND status='ACTIVE' AND (expiry_date IS NULL OR expiry_date >= CURRENT_TIMESTAMP)";
        try(PreparedStatement ps=connection.prepareStatement(sql)){ps.setString(1,code);try(ResultSet rs=ps.executeQuery()){
            if(!rs.next())return Optional.empty();Coupon c=new Coupon();c.setCouponId(rs.getInt("coupon_id"));c.setCode(rs.getString("code"));c.setDiscountType(rs.getString("discount_type"));c.setDiscountValue(rs.getBigDecimal("discount_value"));c.setMinimumOrder(rs.getBigDecimal("minimum_order"));c.setMaximumDiscount(rs.getBigDecimal("maximum_discount"));Timestamp t=rs.getTimestamp("expiry_date");if(t!=null)c.setExpiryDate(t.toLocalDateTime());c.setStatus(rs.getString("status"));return Optional.of(c);
        }}
    }

    public List<Coupon> findAll() throws SQLException {
        List<Coupon> out=new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement("SELECT * FROM coupons ORDER BY coupon_id DESC");ResultSet rs=ps.executeQuery()){
            while(rs.next()){Coupon c=new Coupon();c.setCouponId(rs.getInt("coupon_id"));c.setCode(rs.getString("code"));c.setDiscountType(rs.getString("discount_type"));c.setDiscountValue(rs.getBigDecimal("discount_value"));c.setMinimumOrder(rs.getBigDecimal("minimum_order"));c.setMaximumDiscount(rs.getBigDecimal("maximum_discount"));Timestamp t=rs.getTimestamp("expiry_date");if(t!=null)c.setExpiryDate(t.toLocalDateTime());c.setStatus(rs.getString("status"));out.add(c);}
        }return out;
    }

    public int create(Coupon c) throws SQLException {
        String sql="INSERT INTO coupons(code,discount_type,discount_value,minimum_order,maximum_discount,expiry_date,status) VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,c.getCode());ps.setString(2,c.getDiscountType());ps.setBigDecimal(3,c.getDiscountValue());ps.setBigDecimal(4,c.getMinimumOrder());ps.setBigDecimal(5,c.getMaximumDiscount());ps.setTimestamp(6,c.getExpiryDate()==null?null:Timestamp.valueOf(c.getExpiryDate()));ps.setString(7,c.getStatus());ps.executeUpdate();try(ResultSet keys=ps.getGeneratedKeys()){if(keys.next())return keys.getInt(1);}
        }return 0;
    }
}
