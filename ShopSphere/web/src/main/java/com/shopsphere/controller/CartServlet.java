package com.shopsphere.controller;

import com.shopsphere.dao.CartDAO;
import com.shopsphere.model.User;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute("user");
        try (Connection connection = DatabaseConnection.open()) {
            request.setAttribute("cartLines", new CartDAO(connection).findLinesByUser(user.getUserId()));
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        } catch (Exception ex) {
            throw new ServletException("Cart could not be loaded.", ex);
        }
    }
}
