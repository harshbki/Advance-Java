package com.shopsphere.swing.view;

import com.shopsphere.swing.dao.AdminDataDAO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CategoryPanel extends JPanel {
    private final AdminDataDAO dao = new AdminDataDAO();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name","Description","Status"},0){ public boolean isCellEditable(int r,int c){return false;} };
    private final JTable table = new JTable(model);
    public CategoryPanel() { super(new BorderLayout(10,10));
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.LEFT)); JButton add=new JButton("Add category"), edit=new JButton("Edit selected"), remove=new JButton("Delete selected"), refresh=new JButton("Refresh");
        actions.add(add); actions.add(edit); actions.add(remove); actions.add(refresh); add(actions,BorderLayout.NORTH); add(new JScrollPane(table),BorderLayout.CENTER);
        add.addActionListener(e->addCategory()); edit.addActionListener(e->editCategory()); remove.addActionListener(e->deleteCategory()); refresh.addActionListener(e->reload()); reload();
    }
    private void reload(){ try{ model.setRowCount(0); for(Object[] row:dao.categories()) model.addRow(row); }catch(Exception e){show(e,"Could not load categories");} }
    private void addCategory(){ JTextField name=new JTextField(), description=new JTextField(); Object[] fields={"Name",name,"Description",description}; if(JOptionPane.showConfirmDialog(this,fields,"Add category",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{if(name.getText().isBlank())throw new IllegalArgumentException();dao.addCategory(name.getText().trim(),description.getText().trim());reload();}catch(Exception e){show(e,"Enter a unique category name.");}} }
    private void editCategory(){ int row=table.getSelectedRow(); if(row<0){show(null,"Select a category first.");return;} int id=(Integer)model.getValueAt(row,0); JTextField name=new JTextField((String)model.getValueAt(row,1)), description=new JTextField((String)model.getValueAt(row,2)); Object[] fields={"Category ID: "+id,"Name",name,"Description",description}; if(JOptionPane.showConfirmDialog(this,fields,"Edit category #"+id,JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{if(name.getText().isBlank())throw new IllegalArgumentException();dao.updateCategory(id,name.getText().trim(),description.getText().trim());reload();}catch(Exception e){show(e,"Could not update category.");}} }
    private void deleteCategory(){ int row=table.getSelectedRow(); if(row<0){show(null,"Select a category first.");return;} try{dao.deleteCategory((Integer)model.getValueAt(row,0));reload();}catch(Exception e){show(e,"A category with products cannot be deleted.");} }
    private void show(Exception e,String text){JOptionPane.showMessageDialog(this,text,"ShopSphere Admin",JOptionPane.ERROR_MESSAGE);}
}
