package com.shopsphere.service;

import com.shopsphere.dao.AddressDAO;
import com.shopsphere.dao.CartDAO;
import com.shopsphere.dao.CouponDAO;
import com.shopsphere.dao.CouponUsageDAO;
import com.shopsphere.dao.OrderDAO;
import com.shopsphere.dao.OrderItemDAO;
import com.shopsphere.dao.PaymentDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.model.CartItem;
import com.shopsphere.model.Coupon;
import com.shopsphere.model.Order;
import com.shopsphere.model.OrderItem;
import com.shopsphere.model.Payment;
import com.shopsphere.model.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Coordinates checkout persistence in one transaction. All DAOs must share this connection. */
public class CheckoutService {
    private final Connection connection;
    private final AddressDAO addressDAO;
    private final CartDAO cartDAO;
    private final CouponDAO couponDAO;
    private final CouponUsageDAO couponUsageDAO;
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final PaymentDAO paymentDAO;
    private final ProductDAO productDAO;

    public CheckoutService(Connection connection, AddressDAO addressDAO, CartDAO cartDAO,
                           CouponDAO couponDAO, CouponUsageDAO couponUsageDAO,
                           OrderDAO orderDAO, OrderItemDAO orderItemDAO,
                           PaymentDAO paymentDAO, ProductDAO productDAO) {
        this.connection = connection; this.addressDAO = addressDAO; this.cartDAO = cartDAO;
        this.couponDAO = couponDAO; this.couponUsageDAO = couponUsageDAO;
        this.orderDAO = orderDAO; this.orderItemDAO = orderItemDAO;
        this.paymentDAO = paymentDAO; this.productDAO = productDAO;
    }

    public int placeOrder(int userId, int addressId, String couponCode) throws SQLException {
        return placeOrder(userId, addressId, couponCode, "CARD");
    }

    public int placeOrder(int userId, int addressId, String couponCode, String paymentMethod) throws SQLException {
        if (userId <= 0 || addressId <= 0) throw new IllegalArgumentException("Customer and address are required.");
        String method = (paymentMethod == null || paymentMethod.isBlank()) ? "CARD" : paymentMethod.toUpperCase();
        boolean isCOD = "COD".equals(method);
        String initialPaymentStatus = isCOD ? "PENDING" : "PAID";
        String txnRef = isCOD ? ("COD-" + System.currentTimeMillis()) : ("TXN-" + System.currentTimeMillis());

        boolean originalAutoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            if (!addressDAO.belongsToUser(addressId, userId))
                throw new IllegalArgumentException("The selected address does not belong to this customer.");

            List<CartItem> cart = cartDAO.findByUser(userId);
            if (cart.isEmpty()) throw new IllegalStateException("Cart is empty.");

            BigDecimal subtotal = BigDecimal.ZERO;
            for (CartItem item : cart) {
                Product product = productDAO.findById(item.getProductId())
                        .orElseThrow(() -> new IllegalStateException("A cart product no longer exists."));
                if (!product.isStatus() || product.getStock() < item.getQuantity())
                    throw new IllegalStateException("A product is inactive or has insufficient stock.");
                subtotal = subtotal.add(effectivePrice(product).multiply(BigDecimal.valueOf(item.getQuantity())));
            }

            BigDecimal discount = BigDecimal.ZERO;
            Coupon appliedCoupon = null;
            if (couponCode != null && !couponCode.isBlank()) {
                appliedCoupon = couponDAO.findActiveByCode(couponCode.trim())
                        .orElseThrow(() -> new IllegalArgumentException("Coupon is invalid or expired."));
                if (couponUsageDAO.hasUserUsedCoupon(appliedCoupon.getCouponId(), userId))
                    throw new IllegalArgumentException("You have already used this coupon.");
                if (subtotal.compareTo(appliedCoupon.getMinimumOrder()) < 0)
                    throw new IllegalArgumentException("Order does not meet the coupon minimum.");
                discount = "PERCENT".equals(appliedCoupon.getDiscountType())
                        ? subtotal.multiply(appliedCoupon.getDiscountValue()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP)
                        : appliedCoupon.getDiscountValue();
                if (appliedCoupon.getMaximumDiscount() != null && discount.compareTo(appliedCoupon.getMaximumDiscount()) > 0)
                    discount = appliedCoupon.getMaximumDiscount();
                if (discount.compareTo(subtotal) > 0) discount = subtotal;
            }

            BigDecimal total = subtotal.subtract(discount);
            Order order = new Order(); order.setUserId(userId); order.setAddressId(addressId);
            order.setTotalAmount(total); order.setDiscount(discount); order.setPaymentMethod(method);
            order.setPaymentStatus(initialPaymentStatus); order.setOrderStatus("PLACED");
            int orderId = orderDAO.create(order);
            if (orderId <= 0) throw new SQLException("Order could not be created.");

            for (CartItem item : cart) {
                Product product = productDAO.findById(item.getProductId())
                        .orElseThrow(() -> new IllegalStateException("A cart product no longer exists."));
                if (!productDAO.decrementStock(product.getProductId(), item.getQuantity()))
                    throw new IllegalStateException("Stock changed during checkout. Please retry.");
                OrderItem orderItem = new OrderItem(); orderItem.setOrderId(orderId);
                orderItem.setProductId(product.getProductId()); orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(effectivePrice(product)); orderItemDAO.create(orderItem);
            }

            if (appliedCoupon != null) couponUsageDAO.record(appliedCoupon.getCouponId(), userId, orderId);
            Payment payment = new Payment(); payment.setOrderId(orderId); payment.setPaymentMethod(method);
            payment.setTransactionReference(txnRef);
            payment.setAmount(total); payment.setPaymentStatus(isCOD ? "PENDING" : "SUCCESS");
            paymentDAO.create(payment);
            cartDAO.clearForUser(userId);
            connection.commit();
            return orderId;
        } catch (SQLException | RuntimeException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(originalAutoCommit);
        }
    }

    private BigDecimal effectivePrice(Product product) {
        return product.getPrice()
                .multiply(BigDecimal.ONE.subtract(product.getDiscount().movePointLeft(2)))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
