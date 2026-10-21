package com.shopsphere.swing.view;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard(){
        super("ShopSphere Admin");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(980,620);
        setLocationRelativeTo(null);

        // Menu bar for separate management frames (RTU Guide Section 21)
        JMenuBar menuBar = new JMenuBar();
        JMenu windowsMenu = new JMenu("Windows");
        
        JMenuItem miProduct = new JMenuItem("Product Management (ProductFrame)");
        miProduct.addActionListener(e -> new ProductFrame().setVisible(true));
        
        JMenuItem miCategory = new JMenuItem("Category Management (CategoryFrame)");
        miCategory.addActionListener(e -> new CategoryFrame().setVisible(true));
        
        JMenuItem miCustomer = new JMenuItem("Customer Management (CustomerFrame)");
        miCustomer.addActionListener(e -> new CustomerFrame().setVisible(true));
        
        JMenuItem miOrder = new JMenuItem("Order Management (OrderFrame)");
        miOrder.addActionListener(e -> new OrderFrame().setVisible(true));
        
        JMenuItem miInventory = new JMenuItem("Inventory & Stock (InventoryFrame)");
        miInventory.addActionListener(e -> new InventoryFrame().setVisible(true));

        windowsMenu.add(miProduct);
        windowsMenu.add(miCategory);
        windowsMenu.add(miCustomer);
        windowsMenu.add(miOrder);
        windowsMenu.add(miInventory);
        menuBar.add(windowsMenu);
        setJMenuBar(menuBar);

        JTabbedPane tabs=new JTabbedPane();
        tabs.addTab("Products",new ProductPanel());
        tabs.addTab("Categories",new CategoryPanel());
        tabs.addTab("Customers",new CustomerPanel());
        tabs.addTab("Orders",new OrderPanel());
        tabs.addTab("Inventory",new InventoryPanel());
        add(tabs,BorderLayout.CENTER);
    }
}
