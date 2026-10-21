package com.shopsphere.swing.view;

import com.shopsphere.swing.dao.AdminDataDAO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OrderPanel extends JPanel {
    private final AdminDataDAO dao=new AdminDataDAO();
    private final DefaultTableModel model=new DefaultTableModel(new Object[]{"Order","User","Total","Payment","Status","Created"},0){public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model);
    public OrderPanel(){super(new BorderLayout(10,10)); JPanel actions=new JPanel(new FlowLayout(FlowLayout.LEFT)); JButton status=new JButton("Update selected status"),refresh=new JButton("Refresh");actions.add(status);actions.add(refresh);add(actions,BorderLayout.NORTH);add(new JScrollPane(table),BorderLayout.CENTER);status.addActionListener(e->updateStatus());refresh.addActionListener(e->reload());reload();}
    private void reload(){try{model.setRowCount(0);for(Object[] row:dao.orders())model.addRow(row);}catch(Exception e){error("Could not load orders.");}}
    private void updateStatus(){int row=table.getSelectedRow();if(row<0){error("Select an order first.");return;} JComboBox<String> status=new JComboBox<>(new String[]{"PLACED","PROCESSING","SHIPPED","DELIVERED","CANCELLED"});if(JOptionPane.showConfirmDialog(this,status,"Order status",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)try{dao.updateOrderStatus((Integer)model.getValueAt(row,0),(String)status.getSelectedItem());reload();}catch(Exception e){error("Could not update order status.");}}
    private void error(String s){JOptionPane.showMessageDialog(this,s,"ShopSphere Admin",JOptionPane.ERROR_MESSAGE);}
}
