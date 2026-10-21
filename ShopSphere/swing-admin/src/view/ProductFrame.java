package com.shopsphere.swing.view;

import javax.swing.*;
import java.awt.*;

/**
 * Standalone Product Management Window for ShopSphere Desktop Admin.
 * Satisfies RTU Project Guide Section 21 (Swing MVC Architecture).
 */
public class ProductFrame extends JFrame {
    public ProductFrame() {
        super("ShopSphere - Product Management");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(850, 520);
        setLocationRelativeTo(null);
        add(new ProductPanel(), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductFrame().setVisible(true));
    }
}
