<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Orders | ShopSphere</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
    <style>
        .order-card { background: #fff; border: 1px solid #e2e8f0; border-radius: 12px; margin-bottom: 1.5rem; overflow: hidden; box-shadow: 0 1px 3px rgba(0,0,0,0.05); transition: transform 0.15s ease; }
        .order-card-header { background: #f8fafc; border-bottom: 1px solid #e2e8f0; padding: 1rem 1.5rem; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 0.75rem; }
        .order-card-body { padding: 1.25rem 1.5rem; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 1rem; }
        .order-meta-label { font-size: 0.75rem; text-transform: uppercase; letter-spacing: 0.05em; color: #64748b; margin-bottom: 0.15rem; }
        .order-meta-value { font-size: 0.95rem; font-weight: 600; color: #1e293b; }
        .badge-status { padding: 0.35rem 0.75rem; border-radius: 9999px; font-size: 0.8rem; font-weight: 600; }
        .badge-placed { background: #e0f2fe; color: #0369a1; }
        .badge-processing { background: #fef3c7; color: #b45309; }
        .badge-shipped { background: #f3e8ff; color: #7e22ce; }
        .badge-delivered { background: #dcfce7; color: #15803d; }
        .badge-cancelled { background: #fee2e2; color: #b91c1c; }
        .badge-paid { background: #dcfce7; color: #15803d; }
        .badge-pending { background: #fef3c7; color: #b45309; }
    </style>
</head>
<body>
<header class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/">ShopSphere</a>
    <nav>
        <a href="${pageContext.request.contextPath}/products">Shop</a>
        <a href="${pageContext.request.contextPath}/cart">Cart</a>
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
        <a href="${pageContext.request.contextPath}/logout">Sign out</a>
    </nav>
</header>

<main class="content">
    <p class="eyebrow">PURCHASE HISTORY</p>
    <h1>Your orders</h1>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="alert alert-info py-4 text-center">
                <h4>No orders found yet</h4>
                <p class="text-muted">You haven't placed any orders with ShopSphere so far.</p>
                <a href="${pageContext.request.contextPath}/products" class="button">Start shopping now</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="orders-container">
                <c:forEach var="order" items="${orders}">
                    <div class="order-card">
                        <!-- Order Header with Timestamp and Total -->
                        <div class="order-card-header">
                            <div>
                                <div class="order-meta-label">ORDER NUMBER</div>
                                <div class="order-meta-value">#<c:out value="${order.orderId}"/></div>
                            </div>
                            <div>
                                <div class="order-meta-label">DATE &amp; TIME PLACED</div>
                                <div class="order-meta-value"><c:out value="${order.createdAt}"/></div>
                            </div>
                            <div>
                                <div class="order-meta-label">TOTAL AMOUNT</div>
                                <div class="order-meta-value" style="color: #0284c7;">₹<c:out value="${order.totalAmount}"/></div>
                            </div>
                            <div>
                                <div class="order-meta-label">PAYMENT</div>
                                <div>
                                    <span class="badge ${order.paymentStatus == 'PAID' ? 'badge-paid' : 'badge-pending'}">
                                        <c:out value="${order.paymentMethod}"/> &bull; <c:out value="${order.paymentStatus}"/>
                                    </span>
                                </div>
                            </div>
                            <div>
                                <div class="order-meta-label">DELIVERY STATUS</div>
                                <div>
                                    <span class="badge-status 
                                        <c:choose>
                                            <c:when test="${order.orderStatus == 'DELIVERED'}">badge-delivered</c:when>
                                            <c:when test="${order.orderStatus == 'SHIPPED'}">badge-shipped</c:when>
                                            <c:when test="${order.orderStatus == 'PROCESSING'}">badge-processing</c:when>
                                            <c:when test="${order.orderStatus == 'CANCELLED'}">badge-cancelled</c:when>
                                            <c:otherwise>badge-placed</c:otherwise>
                                        </c:choose>">
                                        ● <c:out value="${order.orderStatus}"/>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <!-- Order Body with Action Link -->
                        <div class="order-card-body">
                            <div class="text-muted small">
                                Package handled by ShopSphere Logistics. Click below for complete itemized receipt and tracking.
                            </div>
                            <div>
                                <a href="${pageContext.request.contextPath}/orders/${order.orderId}" class="button" style="padding: 0.5rem 1.25rem; font-size: 0.9rem;">
                                    View order details &amp; tracking &rarr;
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<footer class="site-footer">
    <span>ShopSphere</span>
    <span>RTU Advanced Java project</span>
</footer>
</body>
</html>
