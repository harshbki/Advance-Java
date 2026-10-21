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

@WebServlet("/cart/remove")
public class CartRemoveServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute("user");
        try {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            try (Connection connection = DatabaseConnection.open()) {
                CartDAO cart = new CartDAO(connection);
                cart.remove(cartId, user.getUserId());
                request.getSession().setAttribute("cartCount", cart.countItemsByUser(user.getUserId()));
            }
            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/cart?error=item");
        } catch (Exception ex) {
            throw new ServletException("Cart item could not be removed.", ex);
        }
    }
}
