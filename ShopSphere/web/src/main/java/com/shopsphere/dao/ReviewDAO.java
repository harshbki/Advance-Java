package com.shopsphere.dao;

import com.shopsphere.model.Review;
import java.sql.*;
import java.util.*;

public class ReviewDAO {
    private final Connection connection;
    public ReviewDAO(Connection connection){this.connection=connection;}
    public int create(Review r) throws SQLException {
        try(PreparedStatement ps=connection.prepareStatement("INSERT INTO reviews(user_id,product_id,rating,review_text) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,r.getUserId());ps.setInt(2,r.getProductId());ps.setInt(3,r.getRating());ps.setString(4,r.getReviewText());ps.executeUpdate();
            try(ResultSet keys=ps.getGeneratedKeys()){if(keys.next())return keys.getInt(1);}
        }return 0;
    }
    public List<Review> findByProduct(int productId) throws SQLException {
        List<Review> out=new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement("SELECT * FROM reviews WHERE product_id=? ORDER BY created_at DESC")){ps.setInt(1,productId);try(ResultSet rs=ps.executeQuery()){
            while(rs.next()){Review r=new Review();r.setReviewId(rs.getInt("review_id"));r.setUserId(rs.getInt("user_id"));r.setProductId(rs.getInt("product_id"));r.setRating(rs.getInt("rating"));r.setReviewText(rs.getString("review_text"));Timestamp t=rs.getTimestamp("created_at");if(t!=null)r.setCreatedAt(t.toLocalDateTime());out.add(r);}
        }}return out;
    }

    public boolean productExists(int productId) throws SQLException {
        try(PreparedStatement ps=connection.prepareStatement("SELECT 1 FROM products WHERE product_id=? AND status='ACTIVE'")){
            ps.setInt(1,productId);try(ResultSet rs=ps.executeQuery()){return rs.next();}
        }
    }
}
