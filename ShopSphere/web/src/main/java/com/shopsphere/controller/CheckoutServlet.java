package com.shopsphere.controller;

import com.shopsphere.dao.AddressDAO;
import com.shopsphere.dao.CartDAO;
import com.shopsphere.dao.CouponDAO;
import com.shopsphere.dao.CouponUsageDAO;
import com.shopsphere.dao.OrderDAO;
import com.shopsphere.dao.OrderItemDAO;
import com.shopsphere.dao.PaymentDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.model.User;
import com.shopsphere.service.CheckoutService;
import com.shopsphere.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute("user");
        try (Connection connection = DatabaseConnection.open()) {
            request.setAttribute("addresses", new AddressDAO(connection).findByUser(user.getUserId()));
            var cartLines = new CartDAO(connection).findLinesByUser(user.getUserId());
            request.setAttribute("cartLines", cartLines);
            java.math.BigDecimal subtotal = cartLines.stream()
                    .map(com.shopsphere.model.CartLine::getLineTotal)
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
            request.setAttribute("subtotal", subtotal);
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);
        } catch (Exception ex) {
            throw new ServletException("Checkout could not be opened.", ex);
        }
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute("user");
        try {
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            String paymentMethod = request.getParameter("paymentMethod");
            try (Connection connection = DatabaseConnection.open()) {
                CheckoutService service = new CheckoutService(connection,
                        new AddressDAO(connection), new CartDAO(connection), new CouponDAO(connection),
                        new CouponUsageDAO(connection), new OrderDAO(connection), new OrderItemDAO(connection),
                        new PaymentDAO(connection), new ProductDAO(connection));
                int orderId = service.placeOrder(user.getUserId(), addressId, request.getParameter("couponCode"), paymentMethod);
                request.getSession().setAttribute("cartCount", 0);
                response.sendRedirect(request.getContextPath() + "/order-success.jsp?orderId=" + orderId);
            }
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage() == null ? "Choose a valid address." : ex.getMessage());
            doGet(request, response);
        } catch (IllegalStateException ex) {
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
        } catch (Exception ex) {
            throw new ServletException("Checkout could not be completed.", ex);
        }
    }
}
