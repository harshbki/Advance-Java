package com.shopsphere.controller;

import com.shopsphere.dao.ProductDAO;
import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.model.Product;
import com.shopsphere.service.ProductService;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection connection = DatabaseConnection.open()) {
            Integer categoryId = null;
            try { if (request.getParameter("category") != null) categoryId = Integer.valueOf(request.getParameter("category")); }
            catch (NumberFormatException ignored) { }
            List<Product> products = new ProductService(new ProductDAO(connection))
                    .searchProducts(request.getParameter("q"), categoryId, request.getParameter("sort"));
            request.setAttribute("products", products);
            request.setAttribute("categories", new CategoryDAO(connection).findActive());
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } catch (SQLException | IllegalStateException ex) {
            throw new ServletException("Products could not be loaded.", ex);
        }
    }
}
