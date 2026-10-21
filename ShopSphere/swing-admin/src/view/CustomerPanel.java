package com.shopsphere.swing.view;

import com.shopsphere.swing.dao.AdminDataDAO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CustomerPanel extends JPanel {
    private final AdminDataDAO dao = new AdminDataDAO();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name","Email","Mobile","Status","Joined"},0){ public boolean isCellEditable(int r,int c){return false;} };
    public CustomerPanel(){ super(new BorderLayout(10,10)); JTable table=new JTable(model); JPanel actions=new JPanel(new FlowLayout(FlowLayout.LEFT)); JButton refresh=new JButton("Refresh customers"); actions.add(refresh); add(actions,BorderLayout.NORTH);add(new JScrollPane(table),BorderLayout.CENTER);refresh.addActionListener(e->reload());reload(); }
    private void reload(){try{model.setRowCount(0);for(Object[] row:dao.customers())model.addRow(row);}catch(Exception e){JOptionPane.showMessageDialog(this,"Could not load customers.","ShopSphere Admin",JOptionPane.ERROR_MESSAGE);}}
}
