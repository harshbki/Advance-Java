package com.shopsphere.dao;

import com.shopsphere.model.Category;
import java.sql.*;
import java.util.*;

public class CategoryDAO {
    private final Connection connection;
    public CategoryDAO(Connection connection) { this.connection = connection; }
    public int create(Category c) throws SQLException {
        String sql="INSERT INTO categories(name,description,status) VALUES(?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,c.getName()); ps.setString(2,c.getDescription()); ps.setString(3,c.isStatus()?"ACTIVE":"INACTIVE"); ps.executeUpdate();
            try(ResultSet keys=ps.getGeneratedKeys()){if(keys.next())return keys.getInt(1);}
        } return 0;
    }
    public List<Category> findAll() throws SQLException {
        List<Category> out=new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement("SELECT * FROM categories ORDER BY name"); ResultSet rs=ps.executeQuery()){
            while(rs.next()){Category c=new Category(); c.setCategoryId(rs.getInt("category_id"));c.setName(rs.getString("name"));c.setDescription(rs.getString("description"));c.setStatus("ACTIVE".equals(rs.getString("status")));out.add(c);}
        } return out;
    }
    public List<Category> findActive() throws SQLException {
        List<Category> out=new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement("SELECT * FROM categories WHERE status='ACTIVE' ORDER BY name");ResultSet rs=ps.executeQuery()){
            while(rs.next()){Category c=new Category();c.setCategoryId(rs.getInt("category_id"));c.setName(rs.getString("name"));c.setDescription(rs.getString("description"));c.setStatus(true);out.add(c);}
        }return out;
    }
    public java.util.Optional<Category> findById(int id) throws SQLException {
        try(PreparedStatement ps=connection.prepareStatement("SELECT * FROM categories WHERE category_id=?")){
            ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){if(!rs.next())return java.util.Optional.empty();Category c=new Category();c.setCategoryId(rs.getInt("category_id"));c.setName(rs.getString("name"));c.setDescription(rs.getString("description"));c.setStatus("ACTIVE".equals(rs.getString("status")));return java.util.Optional.of(c);}
        }
    }
    public boolean update(Category c) throws SQLException {
        try(PreparedStatement ps=connection.prepareStatement("UPDATE categories SET name=?,description=?,status=? WHERE category_id=?")){
            ps.setString(1,c.getName());ps.setString(2,c.getDescription());ps.setString(3,c.isStatus()?"ACTIVE":"INACTIVE");ps.setInt(4,c.getCategoryId());return ps.executeUpdate()==1;
        }
    }
    public boolean delete(int id) throws SQLException {
        try(PreparedStatement ps=connection.prepareStatement("DELETE FROM categories WHERE category_id=?")){ps.setInt(1,id);return ps.executeUpdate()==1;}
    }
}
