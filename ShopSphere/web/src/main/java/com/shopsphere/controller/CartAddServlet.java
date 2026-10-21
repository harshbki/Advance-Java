package com.shopsphere.controller;

import com.shopsphere.dao.CartDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.model.Product;
import com.shopsphere.model.User;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/cart/add")
public class CartAddServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute("user");
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity < 1) throw new IllegalArgumentException("Choose at least one item.");
            try (Connection connection = DatabaseConnection.open()) {
                ProductDAO products = new ProductDAO(connection);
                Product product = products.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found."));
                CartDAO cart = new CartDAO(connection);
                if (!product.isStatus() || cart.findQuantity(user.getUserId(), productId) + quantity > product.getStock())
                    throw new IllegalArgumentException("Requested quantity is not available.");
                cart.addOrIncrease(user.getUserId(), productId, quantity);
                request.getSession().setAttribute("cartCount", cart.countItemsByUser(user.getUserId()));
            }
            response.sendRedirect(request.getContextPath() + "/cart?added=1");
        } catch (IllegalArgumentException ex) {
            response.sendRedirect(request.getContextPath() + "/products?error=invalid");
        } catch (Exception ex) {
            throw new ServletException("Item could not be added to cart.", ex);
        }
    }
}
