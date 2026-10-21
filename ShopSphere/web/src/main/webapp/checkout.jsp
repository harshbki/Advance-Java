<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Secure Checkout & Payment | ShopSphere</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
    <style>
        .checkout-layout { display: grid; grid-template-columns: 1.5fr 1fr; gap: 2rem; align-items: start; margin-top: 1.5rem; }
        @media (max-width: 900px) { .checkout-layout { grid-template-columns: 1fr; } }
        .checkout-step { background: #fff; border: 1px solid #e2e8f0; border-radius: 12px; padding: 1.5rem; margin-bottom: 1.5rem; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .step-heading { font-size: 1.15rem; font-weight: 700; color: #1e293b; margin-bottom: 1rem; display: flex; align-items: center; gap: 0.5rem; }
        .step-badge { background: #0284c7; color: #fff; width: 26px; height: 26px; border-radius: 50%; display: inline-flex; align-items: center; justify-content: center; font-size: 0.85rem; }
        .payment-option-card { border: 1px solid #cbd5e1; border-radius: 8px; padding: 1rem; margin-bottom: 0.75rem; cursor: pointer; transition: all 0.2s ease; }
        .payment-option-card:hover { border-color: #0284c7; background: #f0f9ff; }
        .payment-option-card input[type="radio"] { margin-right: 0.75rem; accent-color: #0284c7; }
        .payment-details-box { background: #f8fafc; border-top: 1px dashed #cbd5e1; padding: 1rem; margin-top: 0.75rem; border-radius: 6px; font-size: 0.9rem; }
        .summary-card { background: #fff; border: 1px solid #e2e8f0; border-radius: 12px; padding: 1.5rem; position: sticky; top: 1rem; box-shadow: 0 2px 6px rgba(0,0,0,0.06); }
        .summary-item { display: flex; gap: 0.75rem; align-items: center; margin-bottom: 1rem; padding-bottom: 0.75rem; border-bottom: 1px solid #f1f5f9; }
        .summary-thumb { width: 56px; height: 56px; object-fit: cover; border-radius: 6px; border: 1px solid #e2e8f0; }
        .summary-meta { flex: 1; }
        .summary-meta h4 { font-size: 0.95rem; margin: 0; font-weight: 600; color: #1e293b; }
        .summary-meta span { font-size: 0.8rem; color: #64748b; }
        .price-breakdown { font-size: 0.95rem; color: #334155; margin-top: 1rem; }
        .price-row { display: flex; justify-content: space-between; margin-bottom: 0.5rem; }
        .price-row.total { font-size: 1.25rem; font-weight: 700; color: #0f172a; border-top: 2px solid #e2e8f0; padding-top: 0.75rem; margin-top: 0.75rem; }
    </style>
</head>
<body>
<header class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/">ShopSphere</a>
    <nav>
        <a href="${pageContext.request.contextPath}/products">Shop</a>
        <a href="${pageContext.request.contextPath}/cart">Back to cart</a>
        <a href="${pageContext.request.contextPath}/orders">My orders</a>
    </nav>
</header>

<main class="content">
    <p class="eyebrow">FINAL STEP</p>
    <h1>Checkout & Payment</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert"><c:out value="${error}"/></div>
    </c:if>

    <c:choose>
        <c:when test="${empty cartLines}">
            <div class="alert alert-info">
                Your cart is currently empty. <a href="${pageContext.request.contextPath}/products" class="alert-link">Browse our products</a> to add items.
            </div>
        </c:when>
        <c:when test="${empty addresses}">
            <div class="alert alert-warning">
                <h4>Delivery Address Required</h4>
                <p>You have not registered any shipping address yet. Please add an address to your profile to complete your checkout.</p>
                <a href="${pageContext.request.contextPath}/profile" class="button">Add delivery address</a>
            </div>
        </c:when>
        <c:otherwise>
            <form method="post" action="${pageContext.request.contextPath}/checkout" id="checkoutForm">
                <div class="checkout-layout">
                    <!-- Left Section: Address & Payment Method -->
                    <div class="checkout-main">
                        <!-- Step 1: Delivery Address -->
                        <div class="checkout-step">
                            <div class="step-heading">
                                <span class="step-badge">1</span> Delivery address
                            </div>
                            <div class="address-options">
                                <c:forEach var="address" items="${addresses}" varStatus="status">
                                    <label class="payment-option-card d-flex align-items-start">
                                        <input type="radio" name="addressId" value="${address.addressId}" ${status.first ? 'checked' : ''} required>
                                        <div>
                                            <strong><c:out value="${address.addressType}"/></strong>: 
                                            <c:out value="${address.addressLine}"/>, 
                                            <c:out value="${address.city}"/>, 
                                            <c:out value="${address.state}"/> - <strong><c:out value="${address.pincode}"/></strong>
                                        </div>
                                    </label>
                                </c:forEach>
                            </div>
                            <p class="small text-muted mt-2 mb-0">Need to ship elsewhere? <a href="${pageContext.request.contextPath}/profile">Add a new address in your profile</a>.</p>
                        </div>

                        <!-- Step 2: Payment Gateway Options -->
                        <div class="checkout-step">
                            <div class="step-heading">
                                <span class="step-badge">2</span> Select payment method
                            </div>
                            <p class="text-muted small">Choose your preferred payment option below. (Simulated test gateway for project evaluation)</p>

                            <!-- Option 1: UPI -->
                            <label class="payment-option-card d-block" onclick="showPaymentFields('upi')">
                                <div class="d-flex align-items-center">
                                    <input type="radio" name="paymentMethod" value="UPI" checked required>
                                    <div>
                                        <strong>📱 UPI (Google Pay, PhonePe, Paytm, BHIM)</strong>
                                        <div class="text-muted small">Instant payment via any UPI App</div>
                                    </div>
                                </div>
                                <div id="upi-details" class="payment-details-box">
                                    <label class="form-label small mb-1">Enter your Virtual Payment Address (VPA / UPI ID):</label>
                                    <input type="text" class="form-control form-control-sm" placeholder="e.g. yourname@oksbi / 9876543210@paytm" value="customer@upi">
                                    <span class="form-text text-success">✓ Zero convenience fees. Instant verification.</span>
                                </div>
                            </label>

                            <!-- Option 2: Credit / Debit Card -->
                            <label class="payment-option-card d-block" onclick="showPaymentFields('card')">
                                <div class="d-flex align-items-center">
                                    <input type="radio" name="paymentMethod" value="CARD" required>
                                    <div>
                                        <strong>💳 Credit / Debit / ATM Card</strong>
                                        <div class="text-muted small">Visa, MasterCard, RuPay, Maestro</div>
                                    </div>
                                </div>
                                <div id="card-details" class="payment-details-box" style="display: none;">
                                    <div class="row g-2">
                                        <div class="col-12">
                                            <label class="form-label small mb-1">Card number</label>
                                            <input type="text" class="form-control form-control-sm" placeholder="4111 2222 3333 4444" maxlength="19" value="4111 2222 3333 4444">
                                        </div>
                                        <div class="col-6">
                                            <label class="form-label small mb-1">Expiry</label>
                                            <input type="text" class="form-control form-control-sm" placeholder="MM/YY" value="12/28">
                                        </div>
                                        <div class="col-6">
                                            <label class="form-label small mb-1">CVV</label>
                                            <input type="password" class="form-control form-control-sm" placeholder="123" maxlength="3" value="789">
                                        </div>
                                    </div>
                                </div>
                            </label>

                            <!-- Option 3: Net Banking -->
                            <label class="payment-option-card d-block" onclick="showPaymentFields('netbanking')">
                                <div class="d-flex align-items-center">
                                    <input type="radio" name="paymentMethod" value="NET_BANKING" required>
                                    <div>
                                        <strong>🏦 Net Banking</strong>
                                        <div class="text-muted small">All major Indian banks supported</div>
                                    </div>
                                </div>
                                <div id="netbanking-details" class="payment-details-box" style="display: none;">
                                    <label class="form-label small mb-1">Select Bank</label>
                                    <select class="form-select form-select-sm">
                                        <option selected>State Bank of India (SBI)</option>
                                        <option>HDFC Bank</option>
                                        <option>ICICI Bank</option>
                                        <option>Axis Bank</option>
                                        <option>Punjab National Bank</option>
                                    </select>
                                </div>
                            </label>

                            <!-- Option 4: Cash on Delivery -->
                            <label class="payment-option-card d-block" onclick="showPaymentFields('cod')">
                                <div class="d-flex align-items-center">
                                    <input type="radio" name="paymentMethod" value="COD" required>
                                    <div>
                                        <strong>💵 Cash on Delivery (COD)</strong>
                                        <div class="text-muted small">Pay cash or scan QR when package arrives</div>
                                    </div>
                                </div>
                                <div id="cod-details" class="payment-details-box" style="display: none;">
                                    <span class="text-muted">You will pay the exact order amount to the delivery courier upon arrival.</span>
                                </div>
                            </label>
                        </div>
                    </div>

                    <!-- Right Section: Order Summary & Placement -->
                    <div class="checkout-sidebar">
                        <div class="summary-card">
                            <h3 class="step-heading">Order summary</h3>

                            <!-- Itemized List -->
                            <div class="summary-items-list mb-3">
                                <c:forEach var="line" items="${cartLines}">
                                    <div class="summary-item">
                                        <img src="${line.product.imageUrl.startsWith('http') ? line.product.imageUrl : pageContext.request.contextPath.concat('/').concat(line.product.imageUrl)}" alt="${line.product.name}" class="summary-thumb" style="object-fit: contain; background: #fff;" onerror="this.src='https://placehold.co/100x100?text=Product'">
                                        <div class="summary-meta">
                                            <h4><c:out value="${line.product.name}"/></h4>
                                            <span>Qty: <c:out value="${line.quantity}"/> &bull; ₹<c:out value="${line.product.price}"/></span>
                                        </div>
                                        <strong>₹<c:out value="${line.lineTotal}"/></strong>
                                    </div>
                                </c:forEach>
                            </div>

                            <!-- Coupon Input -->
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Have a promo coupon?</label>
                                <div class="input-group input-group-sm">
                                    <input type="text" name="couponCode" class="form-control" placeholder="e.g. WELCOME10, FESTIVE20" maxlength="60">
                                </div>
                                <span class="form-text text-muted" style="font-size: 0.75rem;">Sample coupons: <code>WELCOME10</code> (10% off), <code>FESTIVE20</code> (20% off), <code>FLAT100</code></span>
                            </div>

                            <!-- Price Breakdown -->
                            <div class="price-breakdown">
                                <div class="price-row">
                                    <span>Items subtotal</span>
                                    <span>₹<c:out value="${subtotal}"/></span>
                                </div>
                                <div class="price-row">
                                    <span>Shipping & handling</span>
                                    <span class="text-success fw-bold">FREE</span>
                                </div>
                                <div class="price-row total">
                                    <span>Estimated total</span>
                                    <span>₹<c:out value="${subtotal}"/></span>
                                </div>
                            </div>

                            <button type="submit" class="button w-100 mt-3" style="padding: 0.85rem; font-size: 1.05rem;">
                                Place Order &amp; Pay
                            </button>
                            <p class="text-center text-muted small mt-2 mb-0">🔒 256-bit SSL encrypted secure checkout</p>
                        </div>
                    </div>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</main>

<script>
function showPaymentFields(type) {
    document.getElementById('upi-details').style.display = (type === 'upi') ? 'block' : 'none';
    document.getElementById('card-details').style.display = (type === 'card') ? 'block' : 'none';
    document.getElementById('netbanking-details').style.display = (type === 'netbanking') ? 'block' : 'none';
    document.getElementById('cod-details').style.display = (type === 'cod') ? 'block' : 'none';
}
</script>

<footer class="site-footer">
    <span>ShopSphere</span>
    <span>RTU Advanced Java project</span>
</footer>
</body>
</html>
