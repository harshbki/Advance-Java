package com.shopsphere.rmi.server;

import com.shopsphere.rmi.remote.InventoryService;import com.shopsphere.rmi.remote.Product;
import java.rmi.RemoteException;import java.sql.Connection;import java.sql.PreparedStatement;import java.sql.ResultSet;import java.sql.SQLException;

public class InventoryServiceImpl implements InventoryService {
    @Override public Product getProduct(int id)throws RemoteException{
        try(Connection c=DatabaseConnection.open();PreparedStatement ps=c.prepareStatement("SELECT product_id,name,price,stock FROM products WHERE product_id=? AND status='ACTIVE'")){
            ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){return rs.next()?new Product(rs.getInt(1),rs.getString(2),rs.getBigDecimal(3),rs.getInt(4)):null;}
        }catch(SQLException ex){throw new RemoteException("Could not load product.",ex);}
    }
    @Override public int getStock(int productId)throws RemoteException{
        try(Connection c=DatabaseConnection.open();PreparedStatement ps=c.prepareStatement("SELECT stock FROM products WHERE product_id=? AND status='ACTIVE'")){
            ps.setInt(1,productId);try(ResultSet rs=ps.executeQuery()){return rs.next()?rs.getInt(1):-1;}
        }catch(SQLException ex){throw new RemoteException("Could not load stock.",ex);}
    }
    @Override public boolean updateStock(int productId,int quantity)throws RemoteException{
        if(quantity<0)throw new RemoteException("Stock cannot be negative.");
        try(Connection c=DatabaseConnection.open();PreparedStatement ps=c.prepareStatement("UPDATE products SET stock=? WHERE product_id=?")){
            ps.setInt(1,quantity);ps.setInt(2,productId);return ps.executeUpdate()==1;
        }catch(SQLException ex){throw new RemoteException("Could not update stock.",ex);}
    }
}
