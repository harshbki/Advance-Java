package com.shopsphere.swing.view;

import com.shopsphere.swing.controller.LoginController;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField email=new JTextField(24);
    private final JPasswordField password=new JPasswordField(24);
    public LoginFrame(){
        super("ShopSphere Admin Login");setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);setSize(430,310);setLocationRelativeTo(null);
        JPanel form=new JPanel(new GridLayout(0,1,10,10));form.setBorder(BorderFactory.createEmptyBorder(30,36,30,36));
        form.add(new JLabel("Admin email"));form.add(email);form.add(new JLabel("Password"));form.add(password);
        JButton login=new JButton("Sign in");form.add(login);add(form);
        LoginController controller=new LoginController(this);login.addActionListener(e->controller.login(email.getText(),password.getPassword()));
        getRootPane().setDefaultButton(login);
    }
}
