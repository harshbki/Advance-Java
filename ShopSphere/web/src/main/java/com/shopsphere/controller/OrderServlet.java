package com.shopsphere.controller;

import com.shopsphere.dao.OrderDAO;
import com.shopsphere.dao.OrderItemDAO;
import com.shopsphere.model.User;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/orders/*")
public class OrderServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute("user");
        String path = request.getPathInfo();
        try (Connection connection = DatabaseConnection.open()) {
            OrderDAO orders = new OrderDAO(connection);
            if (path == null || "/".equals(path)) {
                request.setAttribute("orders", orders.findByUser(user.getUserId()));
                request.getRequestDispatcher("/orders.jsp").forward(request, response);
                return;
            }
            try {
                int orderId = Integer.parseInt(path.substring(1));
                var order = orders.findByIdForUser(orderId, user.getUserId());
                var orderObj = order.get();
                request.setAttribute("order", orderObj);
                request.setAttribute("items", new OrderItemDAO(connection).findByOrder(orderId));
                request.setAttribute("address", new com.shopsphere.dao.AddressDAO(connection).findById(orderObj.getAddressId()).orElse(null));
                request.setAttribute("payment", new com.shopsphere.dao.PaymentDAO(connection).findByOrder(orderId).orElse(null));
                request.getRequestDispatcher("/order-details.jsp").forward(request, response);
            } catch (NumberFormatException ex) { response.sendError(HttpServletResponse.SC_NOT_FOUND); }
        } catch (Exception ex) { throw new ServletException("Orders could not be loaded.", ex); }
    }
}
