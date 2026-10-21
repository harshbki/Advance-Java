package com.shopsphere.swing.view;

import com.shopsphere.swing.controller.ProductController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductPanel extends JPanel {
    private final DefaultTableModel model=new DefaultTableModel(new Object[]{"ID","Category ID","Name","Price","Stock"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model);
    private final JCheckBox lowStock=new JCheckBox("Low stock only");
    public ProductPanel(){
        super(new BorderLayout(10,10));ProductController controller=new ProductController(this);
        add(new JScrollPane(table),BorderLayout.CENTER);
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.LEFT));JButton add=new JButton("Add product");JButton edit=new JButton("Edit selected");JButton remove=new JButton("Delete selected");
        JRadioButton all=new JRadioButton("All",true);JRadioButton available=new JRadioButton("In stock");ButtonGroup group=new ButtonGroup();group.add(all);group.add(available);
        actions.add(add);actions.add(edit);actions.add(remove);actions.add(all);actions.add(available);actions.add(lowStock);add(actions,BorderLayout.NORTH);
        add.addActionListener(e->showAddDialog(controller));edit.addActionListener(e->showEditDialog(controller));remove.addActionListener(e->controller.deleteSelected());
        lowStock.addActionListener(e->controller.reload());all.addActionListener(e->controller.reload());available.addActionListener(e->controller.reload());
    }
    private void showAddDialog(ProductController controller){
        JTextField category=new JTextField("1"),name=new JTextField(),price=new JTextField(),stock=new JTextField("0");
        JCheckBox active=new JCheckBox("Active",true);JRadioButton percentage=new JRadioButton("Discount available",false);
        JComboBox<String> categoryType=new JComboBox<>(new String[]{"Choose by category ID"});
        Object[] fields={"Category ID",category,"Category selector",categoryType,"Name",name,"Price",price,"Stock",stock,active,percentage};
        if(JOptionPane.showConfirmDialog(this,fields,"Add product",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)controller.add(category.getText(),name.getText(),price.getText(),stock.getText());
    }
    private void showEditDialog(ProductController controller){
        int row=table.getSelectedRow();if(row<0){JOptionPane.showMessageDialog(this,"Select a product to edit first.");return;}
        int id=(int)model.getValueAt(row,0);
        JTextField category=new JTextField(String.valueOf(model.getValueAt(row,1)));
        JTextField name=new JTextField(String.valueOf(model.getValueAt(row,2)));
        JTextField price=new JTextField(String.valueOf(model.getValueAt(row,3)));
        JTextField stock=new JTextField(String.valueOf(model.getValueAt(row,4)));
        Object[] fields={"Product ID: "+id,"Category ID",category,"Name",name,"Price",price,"Stock",stock};
        if(JOptionPane.showConfirmDialog(this,fields,"Edit product #"+id,JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            controller.update(id,category.getText(),name.getText(),price.getText(),stock.getText());
        }
    }
    public DefaultTableModel getModel(){return model;}public JTable getTable(){return table;}public boolean isLowStockOnly(){return lowStock.isSelected();}
}
