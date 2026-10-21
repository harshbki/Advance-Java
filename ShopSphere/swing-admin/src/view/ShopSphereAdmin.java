package com.shopsphere.swing.view;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;

public class ShopSphereAdmin {
    public static void main(String[] args){
        try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ignored){}
        SwingUtilities.invokeLater(()->new LoginFrame().setVisible(true));
    }
}
