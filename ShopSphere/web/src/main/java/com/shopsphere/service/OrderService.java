package com.shopsphere.service;

import com.shopsphere.dao.OrderDAO;
import com.shopsphere.dao.OrderItemDAO;
import com.shopsphere.model.Order;
import com.shopsphere.model.OrderItem;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    public OrderService(OrderDAO orderDAO, OrderItemDAO orderItemDAO) {
        this.orderDAO = orderDAO; this.orderItemDAO = orderItemDAO;
    }

    public List<Order> getCustomerOrders(int userId) throws SQLException {
        if (userId <= 0) throw new IllegalArgumentException("User id must be positive.");
        return orderDAO.findByUser(userId);
    }

    public List<OrderItem> getOrderItems(int orderId) throws SQLException {
        if (orderId <= 0) throw new IllegalArgumentException("Order id must be positive.");
        return orderItemDAO.findByOrder(orderId);
    }

    public boolean updateOrderStatus(int orderId, String status) throws SQLException {
        if (orderId <= 0) throw new IllegalArgumentException("Order id must be positive.");
        if (!List.of("PLACED", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED").contains(status))
            throw new IllegalArgumentException("Unknown order status.");
        return orderDAO.updateStatus(orderId, status);
    }
}
