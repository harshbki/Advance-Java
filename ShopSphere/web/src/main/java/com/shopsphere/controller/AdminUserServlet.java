package com.shopsphere.controller;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try(Connection c=DatabaseConnection.open()){request.setAttribute("customers",new UserDAO(c).findAllCustomers());request.getRequestDispatcher("/admin/users.jsp").forward(request,response);}
        catch(Exception ex){throw new ServletException("Customers could not be loaded.",ex);}
    }
}
