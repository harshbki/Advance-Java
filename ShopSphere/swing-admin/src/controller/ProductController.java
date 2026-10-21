package com.shopsphere.swing.controller;

import com.shopsphere.swing.dao.ProductDAO;
import com.shopsphere.swing.model.Product;
import com.shopsphere.swing.view.ProductPanel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;

public class ProductController {
    private final ProductPanel view;
    private final ProductDAO dao=new ProductDAO();
    public ProductController(ProductPanel view){this.view=view;reload();}
    public void reload(){
        try{DefaultTableModel model=view.getModel();model.setRowCount(0);for(Product p:dao.findAll()){
            if(view.isLowStockOnly()&&p.getStock()>5)continue;
            model.addRow(new Object[]{p.getProductId(),p.getCategoryId(),p.getName(),p.getPrice(),p.getStock()});
        }}catch(Exception ex){JOptionPane.showMessageDialog(view,ex.getMessage(),"Could not load products",JOptionPane.ERROR_MESSAGE);}
    }
    public void add(String category,String name,String price,String stock){
        try{Product p=new Product();p.setCategoryId(Integer.parseInt(category));p.setName(name.trim());p.setPrice(new BigDecimal(price));p.setStock(Integer.parseInt(stock));
            if(p.getCategoryId()<=0||p.getName().isEmpty()||p.getPrice().signum()<0||p.getStock()<0)throw new IllegalArgumentException();
            dao.add(p);reload();
        }catch(Exception ex){JOptionPane.showMessageDialog(view,"Check category, name, price, and stock.","Product not saved",JOptionPane.ERROR_MESSAGE);}
    }
    public void deleteSelected(){
        int row=view.getTable().getSelectedRow();if(row<0){JOptionPane.showMessageDialog(view,"Select a product first.");return;}
        try{dao.delete((int)view.getModel().getValueAt(row,0));reload();}
        catch(Exception ex){JOptionPane.showMessageDialog(view,"Product may be linked to an order or cart.","Could not delete",JOptionPane.ERROR_MESSAGE);}
    }
    public void update(int id,String category,String name,String price,String stock){
        try{Product p=new Product();p.setProductId(id);p.setCategoryId(Integer.parseInt(category));p.setName(name.trim());p.setPrice(new BigDecimal(price));p.setStock(Integer.parseInt(stock));
            if(p.getCategoryId()<=0||p.getName().isEmpty()||p.getPrice().signum()<0||p.getStock()<0)throw new IllegalArgumentException();
            dao.update(p);reload();
        }catch(Exception ex){JOptionPane.showMessageDialog(view,"Check category, name, price, and stock.","Product not updated",JOptionPane.ERROR_MESSAGE);}
    }
}
