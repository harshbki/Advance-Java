package com.shopsphere.dao;

import com.shopsphere.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {
    private final Connection connection;
    public WishlistDAO(Connection connection){this.connection=connection;}
    public void add(int userId,int productId)throws SQLException{
        try(PreparedStatement ps=connection.prepareStatement("INSERT IGNORE INTO wishlist(user_id,product_id) VALUES(?,?)")){ps.setInt(1,userId);ps.setInt(2,productId);ps.executeUpdate();}
    }
    public boolean remove(int userId,int productId)throws SQLException{
        try(PreparedStatement ps=connection.prepareStatement("DELETE FROM wishlist WHERE user_id=? AND product_id=?")){ps.setInt(1,userId);ps.setInt(2,productId);return ps.executeUpdate()==1;}
    }
    public List<Product> findProducts(int userId)throws SQLException{
        List<Product> out=new ArrayList<>();String sql="SELECT p.* FROM wishlist w JOIN products p ON p.product_id=w.product_id WHERE w.user_id=? ORDER BY w.created_at DESC";
        try(PreparedStatement ps=connection.prepareStatement(sql)){ps.setInt(1,userId);try(ResultSet rs=ps.executeQuery()){
            while(rs.next()){Product p=new Product();p.setProductId(rs.getInt("product_id"));p.setCategoryId(rs.getInt("category_id"));p.setName(rs.getString("name"));p.setBrand(rs.getString("brand"));p.setDescription(rs.getString("description"));p.setPrice(rs.getBigDecimal("price"));p.setDiscount(rs.getBigDecimal("discount"));p.setStock(rs.getInt("stock"));p.setImageUrl(rs.getString("image_url"));p.setStatus("ACTIVE".equals(rs.getString("status")));out.add(p);}
        }}return out;
    }
}
