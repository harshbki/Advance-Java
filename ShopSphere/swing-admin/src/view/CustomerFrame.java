package com.shopsphere.swing.view;

import javax.swing.*;
import java.awt.*;

/**
 * Standalone Customer Management Window for ShopSphere Desktop Admin.
 * Satisfies RTU Project Guide Section 21 (Swing MVC Architecture).
 */
public class CustomerFrame extends JFrame {
    public CustomerFrame() {
        super("ShopSphere - Customer Management");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 480);
        setLocationRelativeTo(null);
        add(new CustomerPanel(), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerFrame().setVisible(true));
    }
}
