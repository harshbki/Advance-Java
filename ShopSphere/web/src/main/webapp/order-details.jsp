<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Order #<c:out value="${order.orderId}"/> Details | ShopSphere</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
    <style>
        .tracking-timeline { display: flex; justify-content: space-between; position: relative; margin: 2rem 0; padding: 0 1rem; }
        .tracking-timeline::before { content: ""; position: absolute; top: 18px; left: 5%; right: 5%; height: 4px; background: #e2e8f0; z-index: 1; }
        .timeline-step { position: relative; z-index: 2; text-align: center; width: 25%; }
        .timeline-dot { width: 38px; height: 38px; border-radius: 50%; background: #fff; border: 3px solid #cbd5e1; display: inline-flex; align-items: center; justify-content: center; font-weight: 700; color: #64748b; margin-bottom: 0.5rem; transition: all 0.3s ease; }
        .timeline-step.completed .timeline-dot { background: #0284c7; border-color: #0284c7; color: #fff; }
        .timeline-step.active .timeline-dot { background: #0284c7; border-color: #38bdf8; color: #fff; box-shadow: 0 0 0 4px rgba(2, 132, 199, 0.2); }
        .timeline-label { font-size: 0.85rem; font-weight: 600; color: #64748b; }
        .timeline-step.completed .timeline-label, .timeline-step.active .timeline-label { color: #0f172a; }

        .order-grid-details { display: grid; grid-template-columns: 1.6fr 1fr; gap: 2rem; align-items: start; margin-top: 1.5rem; }
        @media (max-width: 900px) { .order-grid-details { grid-template-columns: 1fr; } }
        .detail-box { background: #fff; border: 1px solid #e2e8f0; border-radius: 12px; padding: 1.5rem; margin-bottom: 1.5rem; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .detail-heading { font-size: 1.15rem; font-weight: 700; color: #1e293b; margin-bottom: 1rem; border-bottom: 1px solid #f1f5f9; padding-bottom: 0.5rem; }
        
        .item-row { display: flex; gap: 1rem; align-items: center; padding: 1rem 0; border-bottom: 1px solid #f1f5f9; }
        .item-row:last-child { border-bottom: none; }
        .item-thumb { width: 70px; height: 70px; object-fit: cover; border-radius: 8px; border: 1px solid #e2e8f0; }
        .item-info { flex: 1; }
        .item-title { font-size: 1rem; font-weight: 600; color: #1e293b; margin-bottom: 0.25rem; }
        .item-sub { font-size: 0.85rem; color: #64748b; }
        .item-price { font-size: 1.05rem; font-weight: 700; color: #0f172a; }

        .price-summary-table { width: 100%; font-size: 0.95rem; }
        .price-summary-table td { padding: 0.35rem 0; }
        .price-summary-table .total-row td { border-top: 2px solid #e2e8f0; padding-top: 0.75rem; font-size: 1.2rem; font-weight: 700; color: #0f172a; }
    </style>
</head>
<body>
<header class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/">ShopSphere</a>
    <nav>
        <a href="${pageContext.request.contextPath}/products">Shop</a>
        <a href="${pageContext.request.contextPath}/cart">Cart</a>
        <a href="${pageContext.request.contextPath}/orders">My orders</a>
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
    </nav>
</header>

<main class="content">
    <div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mb-2">
        <div>
            <p class="eyebrow mb-1">ORDER CONFIRMATION &amp; TRACKING</p>
            <h1>Order #<c:out value="${order.orderId}"/></h1>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/orders" class="button" style="background: #f1f5f9; color: #334155; border: 1px solid #cbd5e1;">
                &larr; Back to all orders
            </a>
        </div>
    </div>

    <!-- Order Timeline Bar -->
    <div class="detail-box">
        <div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mb-3">
            <div>
                <span class="text-muted small">Placed on:</span> 
                <strong><c:out value="${order.createdAt}"/></strong>
            </div>
            <div>
                <span class="text-muted small">Current status:</span>
                <span class="badge bg-primary text-uppercase px-3 py-2"><c:out value="${order.orderStatus}"/></span>
            </div>
        </div>

        <div class="tracking-timeline">
            <!-- Step 1: Placed -->
            <div class="timeline-step completed">
                <div class="timeline-dot">✓</div>
                <div class="timeline-label">Order Placed</div>
            </div>
            <!-- Step 2: Processing -->
            <div class="timeline-step ${order.orderStatus == 'PROCESSING' || order.orderStatus == 'SHIPPED' || order.orderStatus == 'DELIVERED' ? 'completed' : ''} ${order.orderStatus == 'PROCESSING' ? 'active' : ''}">
                <div class="timeline-dot">2</div>
                <div class="timeline-label">Processing</div>
            </div>
            <!-- Step 3: Shipped -->
            <div class="timeline-step ${order.orderStatus == 'SHIPPED' || order.orderStatus == 'DELIVERED' ? 'completed' : ''} ${order.orderStatus == 'SHIPPED' ? 'active' : ''}">
                <div class="timeline-dot">3</div>
                <div class="timeline-label">Shipped</div>
            </div>
            <!-- Step 4: Delivered -->
            <div class="timeline-step ${order.orderStatus == 'DELIVERED' ? 'completed active' : ''}">
                <div class="timeline-dot">4</div>
                <div class="timeline-label">Delivered</div>
            </div>
        </div>
    </div>

    <!-- Main Content: Left Items, Right Address & Payment -->
    <div class="order-grid-details">
        <!-- Left: Items list -->
        <div class="items-container">
            <div class="detail-box">
                <div class="detail-heading">Purchased items (<c:out value="${items.size()}"/>)</div>
                <c:forEach var="item" items="${items}">
                    <div class="item-row">
                        <img src="${item.productImage}" alt="${item.productName}" class="item-thumb" onerror="this.src='https://placehold.co/100x100?text=Product'">
                        <div class="item-info">
                            <div class="item-title"><c:out value="${item.productName}"/></div>
                            <div class="item-sub">
                                <c:if test="${not empty item.brand}"><c:out value="${item.brand}"/> &bull; </c:if>
                                Unit Price: ₹<c:out value="${item.price}"/> &bull; Quantity: <strong><c:out value="${item.quantity}"/></strong>
                            </div>
                        </div>
                        <div class="item-price">
                            ₹<c:out value="${item.price * item.quantity}"/>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- Right: Shipping, Payment & Order Summary -->
        <div class="sidebar-container">
            <!-- Delivery Address -->
            <div class="detail-box">
                <div class="detail-heading">Delivery address</div>
                <c:choose>
                    <c:when test="${not empty address}">
                        <p class="mb-1"><strong><c:out value="${address.addressType}"/> Address</strong></p>
                        <p class="mb-1 text-muted"><c:out value="${address.addressLine}"/></p>
                        <p class="mb-0 text-muted"><c:out value="${address.city}"/>, <c:out value="${address.state}"/> - <c:out value="${address.pincode}"/></p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-muted">Standard Registered Customer Address</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Payment Details -->
            <div class="detail-box">
                <div class="detail-heading">Payment details</div>
                <p class="mb-1">
                    <span class="text-muted">Payment method:</span> 
                    <strong><c:out value="${order.paymentMethod}"/></strong>
                </p>
                <p class="mb-1">
                    <span class="text-muted">Payment status:</span> 
                    <span class="badge ${order.paymentStatus == 'PAID' || order.paymentStatus == 'SUCCESS' ? 'bg-success' : 'bg-warning text-dark'}">
                        <c:out value="${order.paymentStatus}"/>
                    </span>
                </p>
                <c:if test="${not empty payment and not empty payment.transactionReference}">
                    <p class="mb-0 small text-muted">
                        Reference: <code><c:out value="${payment.transactionReference}"/></code>
                    </p>
                </c:if>
            </div>

            <!-- Financial Summary -->
            <div class="detail-box">
                <div class="detail-heading">Payment summary</div>
                <table class="price-summary-table">
                    <tr>
                        <td>Order Subtotal</td>
                        <td class="text-end">₹<c:out value="${order.totalAmount + order.discount}"/></td>
                    </tr>
                    <c:if test="${order.discount > 0}">
                        <tr class="text-success">
                            <td>Coupon Discount</td>
                            <td class="text-end">- ₹<c:out value="${order.discount}"/></td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>Shipping Fee</td>
                        <td class="text-end text-success">FREE</td>
                    </tr>
                    <tr class="total-row">
                        <td>Total Amount Paid</td>
                        <td class="text-end" style="color: #0284c7;">₹<c:out value="${order.totalAmount}"/></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</main>

<footer class="site-footer">
    <span>ShopSphere</span>
    <span>RTU Advanced Java project</span>
</footer>
</body>
</html>
