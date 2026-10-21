package com.shopsphere.swing.view;

import javax.swing.*;
import java.awt.*;

/**
 * Standalone Category Management Window for ShopSphere Desktop Admin.
 * Satisfies RTU Project Guide Section 21 (Swing MVC Architecture).
 */
public class CategoryFrame extends JFrame {
    public CategoryFrame() {
        super("ShopSphere - Category Management");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(750, 480);
        setLocationRelativeTo(null);
        add(new CategoryPanel(), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CategoryFrame().setVisible(true));
    }
}
