package com.shopsphere.swing.view;

import com.shopsphere.swing.dao.AdminDataDAO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InventoryPanel extends JPanel {
    private final AdminDataDAO dao=new AdminDataDAO();
    private final DefaultTableModel model=new DefaultTableModel(new Object[]{"Product ID","Name","Stock"},0){public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model);
    public InventoryPanel(){super(new BorderLayout(10,10));JPanel actions=new JPanel(new FlowLayout(FlowLayout.LEFT));JButton stock=new JButton("Set selected stock"),refresh=new JButton("Refresh low stock");actions.add(stock);actions.add(refresh);add(actions,BorderLayout.NORTH);add(new JScrollPane(table),BorderLayout.CENTER);stock.addActionListener(e->setStock());refresh.addActionListener(e->reload());reload();}
    private void reload(){try{model.setRowCount(0);for(Object[] row:dao.lowStock())model.addRow(row);}catch(Exception e){error("Could not load low-stock products.");}}
    private void setStock(){int row=table.getSelectedRow();if(row<0){error("Select a product first.");return;}JTextField value=new JTextField(String.valueOf(model.getValueAt(row,2)));if(JOptionPane.showConfirmDialog(this,value,"New stock",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)try{int stock=Integer.parseInt(value.getText());if(stock<0)throw new NumberFormatException();dao.updateStock((Integer)model.getValueAt(row,0),stock);reload();}catch(Exception e){error("Enter stock as zero or a positive number.");}}
    private void error(String s){JOptionPane.showMessageDialog(this,s,"ShopSphere Admin",JOptionPane.ERROR_MESSAGE);}
}
