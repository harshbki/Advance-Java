package com.shopsphere.swing.controller;

import com.shopsphere.swing.dao.UserDAO;
import com.shopsphere.swing.view.AdminDashboard;
import com.shopsphere.swing.view.LoginFrame;
import javax.swing.JOptionPane;

public class LoginController {
    private final LoginFrame view;
    private final UserDAO userDAO=new UserDAO();
    public LoginController(LoginFrame view){this.view=view;}
    public void login(String email,char[] password){
        try{if(userDAO.isAdmin(email,password)){view.dispose();new AdminDashboard().setVisible(true);}else JOptionPane.showMessageDialog(view,"Admin email or password is incorrect.","Sign in failed",JOptionPane.ERROR_MESSAGE);}
        catch(Exception ex){JOptionPane.showMessageDialog(view,ex.getMessage(),"Database connection",JOptionPane.ERROR_MESSAGE);}
        finally{java.util.Arrays.fill(password,'\0');}
    }
}
