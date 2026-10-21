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

@WebServlet("/cart/update")
public class CartUpdateServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute("user");
        try {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity < 1) throw new IllegalArgumentException();
            try (Connection connection = DatabaseConnection.open()) {
                CartDAO cart = new CartDAO(connection);
                var line = cart.findLinesByUser(user.getUserId()).stream()
                        .filter(row -> row.getCartId() == cartId).findFirst().orElseThrow(IllegalArgumentException::new);
                if (quantity > line.getProduct().getStock()) throw new IllegalArgumentException();
                cart.updateQuantity(cartId, user.getUserId(), quantity);
                request.getSession().setAttribute("cartCount", cart.countItemsByUser(user.getUserId()));
            }
            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (IllegalArgumentException ex) {
            response.sendRedirect(request.getContextPath() + "/cart?error=quantity");
        } catch (Exception ex) {
            throw new ServletException("Cart quantity could not be changed.", ex);
        }
    }
}
