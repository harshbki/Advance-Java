package com.shopsphere.swing.view;

import javax.swing.*;
import java.awt.*;

/**
 * Standalone Order Management Window for ShopSphere Desktop Admin.
 * Satisfies RTU Project Guide Section 21 (Swing MVC Architecture).
 */
public class OrderFrame extends JFrame {
    public OrderFrame() {
        super("ShopSphere - Order Management");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(850, 500);
        setLocationRelativeTo(null);
        add(new OrderPanel(), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrderFrame().setVisible(true));
    }
}
