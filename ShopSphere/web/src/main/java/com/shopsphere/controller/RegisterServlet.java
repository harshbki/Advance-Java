package com.shopsphere.controller;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.service.AuthService;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try (Connection connection = DatabaseConnection.open()) {
            AuthService auth = new AuthService(new UserDAO(connection));
            auth.register(request.getParameter("name"), request.getParameter("email"),
                    request.getParameter("password"), request.getParameter("mobile"));
            response.sendRedirect(request.getContextPath() + "/login?registered=1");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } catch (SQLException | IllegalStateException ex) {
            throw new ServletException("Registration could not be completed.", ex);
        }
    }
}
