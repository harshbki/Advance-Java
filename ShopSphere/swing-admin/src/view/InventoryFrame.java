package com.shopsphere.swing.view;

import javax.swing.*;
import java.awt.*;

/**
 * Standalone Inventory & Low Stock Window for ShopSphere Desktop Admin.
 * Satisfies RTU Project Guide Section 21 (Swing MVC Architecture).
 */
public class InventoryFrame extends JFrame {
    public InventoryFrame() {
        super("ShopSphere - Inventory & Low Stock");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 480);
        setLocationRelativeTo(null);
        add(new InventoryPanel(), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryFrame().setVisible(true));
    }
}
