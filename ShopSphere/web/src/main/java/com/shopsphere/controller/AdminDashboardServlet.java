package com.shopsphere.controller;

import com.shopsphere.dao.AdminSummaryDAO;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        try(Connection connection=DatabaseConnection.open()){
            request.setAttribute("summary",new AdminSummaryDAO(connection).load());
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request,response);
        }catch(Exception ex){throw new ServletException("Admin dashboard could not be loaded.",ex);}
    }
}
