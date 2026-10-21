package com.shopsphere.swing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean isAdmin(String email,char[] password)throws SQLException{
        try(Connection c=Database.open();PreparedStatement ps=c.prepareStatement("SELECT password,status FROM users WHERE email=? AND role='ADMIN'")){
            ps.setString(1,email.trim().toLowerCase());try(ResultSet rs=ps.executeQuery()){return rs.next()&&"ACTIVE".equals(rs.getString("status"))&&PasswordHash.verify(password,rs.getString("password"));}
        }
    }
}
