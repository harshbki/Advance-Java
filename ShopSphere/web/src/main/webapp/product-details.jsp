<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="shop" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${product.name}"/> | ShopSphere</title>
    <!-- Bootstrap 5 & Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
    <style>
        .product-detail-page {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1.25rem;
        }
        .detail-img-box {
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            height: 440px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
            padding: 1.5rem;
            box-shadow: 0 4px 12px rgba(0,0,0,0.03);
        }
        .detail-img-box img {
            max-width: 100%;
            max-height: 100%;
            width: auto;
            height: auto;
            object-fit: contain;
            transition: transform 0.3s ease;
        }
        .detail-img-box:hover img {
            transform: scale(1.03);
        }
        .trust-badge-card {
            background: #f8fafc;
            border: 1px solid #e2e8f0;
            border-radius: 8px;
            padding: 0.85rem;
            text-align: center;
            font-size: 0.82rem;
            font-weight: 500;
            color: #475569;
        }
        .trust-badge-card i {
            font-size: 1.35rem;
            display: block;
            margin-bottom: 0.35rem;
            color: #335c45;
        }
        .action-btn-group {
            display: flex;
            gap: 0.75rem;
            align-items: center;
            flex-wrap: wrap;
            margin-top: 1.5rem;
        }
        .qty-picker {
            width: 85px;
            height: 48px;
            border: 1px solid #cbd5e1;
            border-radius: 6px;
            text-align: center;
            font-size: 1.05rem;
            font-weight: 600;
        }
        .btn-cart-main {
            flex: 1;
            min-width: 180px;
            height: 48px;
            background-color: #335c45;
            color: #ffffff;
            border: none;
            border-radius: 6px;
            font-weight: 600;
            font-size: 1rem;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            transition: background 0.2s ease;
            text-decoration: none;
        }
        .btn-cart-main:hover {
            background-color: #274735;
            color: #ffffff;
        }
        .btn-wishlist-main {
            height: 48px;
            padding: 0 1.25rem;
            border: 1px solid #cbd5e1;
            background: #ffffff;
            color: #334155;
            border-radius: 6px;
            font-weight: 600;
            font-size: 0.95rem;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.4rem;
            transition: all 0.2s ease;
            cursor: pointer;
        }
        .btn-wishlist-main:hover {
            background-color: #fff1f2;
            border-color: #fda4af;
            color: #e11d48;
        }
        .review-card-item {
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            padding: 1.25rem;
            margin-bottom: 1rem;
        }
        .rating-stars {
            color: #f59e0b;
            letter-spacing: 2px;
            font-size: 1.15rem;
        }
        .recent-card {
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            overflow: hidden;
            transition: transform 0.2s, box-shadow 0.2s;
            text-decoration: none;
            color: inherit;
            display: block;
        }
        .recent-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 16px rgba(0,0,0,0.06);
            color: inherit;
        }
        .recent-img {
            height: 140px;
            background: #f8fafc;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
            padding: 0.75rem;
        }
        .recent-img img {
            max-width: 100%;
            max-height: 100%;
            object-fit: contain;
        }
    </style>
</head>
<body>
<%@ include file="fragments/header.jsp" %>

<main class="product-detail-page">
    <!-- Breadcrumb -->
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/" class="text-decoration-none">Home</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/products" class="text-decoration-none">Shop</a></li>
            <c:if test="${not empty product.brand}">
                <li class="breadcrumb-item text-muted"><c:out value="${product.brand}"/></li>
            </c:if>
            <li class="breadcrumb-item active" aria-current="page"><c:out value="${product.name}"/></li>
        </ol>
    </nav>

    <!-- Feedback Notice -->
    <c:if test="${not empty param.review}">
        <div class="alert alert-success alert-dismissible fade show mb-4" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> Thank you! Your product review has been submitted successfully.
        </div>
    </c:if>

    <!-- Product Showcase Grid (2-Columns) -->
    <div class="row g-5 align-items-start">
        <!-- Left Column: Product Image & Trust Badges -->
        <div class="col-lg-6">
            <div class="detail-img-box">
                <c:choose>
                    <c:when test="${not empty product.imageUrl}">
                        <c:set var="resolvedProductImg" value="${product.imageUrl.startsWith('http') ? product.imageUrl : (product.imageUrl.startsWith('/') ? pageContext.request.contextPath.concat(product.imageUrl) : pageContext.request.contextPath.concat('/').concat(product.imageUrl))}" />
                        <img src="${resolvedProductImg}" alt="<c:out value='${product.name}'/>">
                    </c:when>
                    <c:otherwise>
                        <div class="text-center text-muted">
                            <i class="bi bi-box-seam display-1"></i>
                            <p class="mt-2 fw-semibold">No Image Available</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Trust Badges -->
            <div class="row g-2 mt-3">
                <div class="col-6 col-sm-3">
                    <div class="trust-badge-card">
                        <i class="bi bi-truck"></i>
                        <span>Free Delivery</span>
                    </div>
                </div>
                <div class="col-6 col-sm-3">
                    <div class="trust-badge-card">
                        <i class="bi bi-shield-check"></i>
                        <span>1 Yr Warranty</span>
                    </div>
                </div>
                <div class="col-6 col-sm-3">
                    <div class="trust-badge-card">
                        <i class="bi bi-arrow-repeat"></i>
                        <span>7 Days Return</span>
                    </div>
                </div>
                <div class="col-6 col-sm-3">
                    <div class="trust-badge-card">
                        <i class="bi bi-patch-check"></i>
                        <span>100% Genuine</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Right Column: Product Meta, Price, Add to Cart & Wishlist -->
        <div class="col-lg-6">
            <c:if test="${not empty product.brand}">
                <span class="badge bg-secondary-subtle text-secondary border border-secondary-subtle px-2 py-1 mb-2">
                    <c:out value="${product.brand}"/>
                </span>
            </c:if>

            <h1 class="display-6 fw-bold mb-2 text-dark"><c:out value="${product.name}"/></h1>

            <!-- Ratings Summary -->
            <div class="d-flex align-items-center gap-2 mb-3">
                <span class="rating-stars">★★★★★</span>
                <span class="text-muted small fw-semibold">
                    <c:choose>
                        <c:when test="${not empty reviews}">
                            (<c:out value="${reviews.size()}"/> verified ratings)
                        </c:when>
                        <c:otherwise>
                            (No reviews yet)
                        </c:otherwise>
                    </c:choose>
                </span>
            </div>

            <!-- Price & Discount -->
            <div class="d-flex align-items-baseline gap-3 mb-3">
                <span class="fs-2 fw-bold text-success">₹<c:out value="${product.price}"/></span>
                <c:if test="${product.discount gt 0}">
                    <span class="badge bg-warning-subtle text-warning border border-warning-subtle fs-6 px-2 py-1">
                        <c:out value="${product.discount}"/>% OFF
                    </span>
                </c:if>
                <span class="text-muted small">Inclusive of all taxes</span>
            </div>

            <!-- Stock Availability Badge -->
            <div class="mb-4">
                <c:choose>
                    <c:when test="${product.stock gt 5}">
                        <span class="badge bg-success-subtle text-success border border-success-subtle px-3 py-2 fs-6">
                            <i class="bi bi-check-circle-fill me-1"></i> In Stock (<c:out value="${product.stock}"/> units available)
                        </span>
                    </c:when>
                    <c:when test="${product.stock gt 0}">
                        <span class="badge bg-warning-subtle text-warning border border-warning-subtle px-3 py-2 fs-6">
                            <i class="bi bi-exclamation-triangle-fill me-1"></i> Only <c:out value="${product.stock}"/> left in stock!
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge bg-danger-subtle text-danger border border-danger-subtle px-3 py-2 fs-6">
                            <i class="bi bi-x-circle-fill me-1"></i> Currently Out of Stock
                        </span>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Description -->
            <p class="text-secondary lead fs-6 mb-4" style="line-height: 1.7;">
                <c:out value="${product.description}"/>
            </p>

            <!-- Actions: Quantity + Add to Cart & Wishlist -->
            <div class="action-btn-group">
                <c:if test="${product.stock gt 0}">
                    <form method="post" action="${pageContext.request.contextPath}/cart/add" class="d-flex gap-2 flex-grow-1 m-0">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <div class="d-flex align-items-center gap-1">
                            <label for="quantityInput" class="visually-hidden">Quantity</label>
                            <input id="quantityInput" class="qty-picker" type="number" name="quantity" value="1" min="1" max="${product.stock}">
                        </div>
                        <button class="btn-cart-main" type="submit">
                            <i class="bi bi-bag-plus"></i> Add to Cart
                        </button>
                    </form>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/wishlist/add" class="m-0">
                    <input type="hidden" name="productId" value="${product.productId}">
                    <button class="btn-wishlist-main" type="submit" title="Save to Wishlist">
                        <i class="bi bi-heart"></i> Wishlist
                    </button>
                </form>
            </div>

            <!-- Product Specifications / Details Summary -->
            <div class="mt-4 pt-3 border-top">
                <table class="table table-sm table-borderless spec-table mb-0">
                    <tbody>
                        <tr>
                            <th class="ps-0">Product ID:</th>
                            <td>#<c:out value="${product.productId}"/></td>
                        </tr>
                        <tr>
                            <th class="ps-0">Brand:</th>
                            <td><c:out value="${not empty product.brand ? product.brand : 'ShopSphere'}"/></td>
                        </tr>
                        <tr>
                            <th class="ps-0">Availability:</th>
                            <td><c:out value="${product.stock > 0 ? 'Ready to Ship' : 'Out of Stock'}"/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Customer Reviews Section -->
    <section class="mt-5 pt-5 border-top">
        <div class="row g-5">
            <!-- Left: Reviews List -->
            <div class="col-lg-7">
                <h3 class="fw-bold mb-4 d-flex align-items-center gap-2">
                    <i class="bi bi-chat-square-quote text-success"></i> Customer Reviews
                    <span class="badge bg-light text-dark border fs-6"><c:out value="${reviews.size()}"/></span>
                </h3>

                <c:forEach var="review" items="${reviews}">
                    <div class="review-card-item">
                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <div class="d-flex align-items-center gap-2">
                                <div class="bg-success-subtle text-success rounded-circle d-flex align-items-center justify-content-center" style="width: 32px; height: 32px; font-weight: 700;">
                                    <i class="bi bi-person-fill"></i>
                                </div>
                                <span class="fw-semibold text-dark">Verified Buyer</span>
                            </div>
                            <span class="text-muted small"><c:out value="${review.createdAt}"/></span>
                        </div>
                        <div class="mb-2">
                            <shop:rating value="${review.rating}"/>
                        </div>
                        <p class="text-secondary mb-0"><c:out value="${review.reviewText}"/></p>
                    </div>
                </c:forEach>

                <c:if test="${empty reviews}">
                    <div class="p-4 border rounded-3 bg-light text-center">
                        <i class="bi bi-chat-left-dots text-muted display-6"></i>
                        <p class="text-muted mt-2 mb-0">No reviews yet for this product. Be the first to review!</p>
                    </div>
                </c:if>
            </div>

            <!-- Right: Write a Review Form -->
            <div class="col-lg-5">
                <div class="card border shadow-sm p-4 rounded-3">
                    <h4 class="fw-bold mb-3"><i class="bi bi-pencil-square text-success me-2"></i>Write a Review</h4>
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <form method="post" action="${pageContext.request.contextPath}/reviews/add">
                                <input type="hidden" name="productId" value="${product.productId}">
                                
                                <div class="mb-3">
                                    <label class="form-label fw-semibold small">Rating</label>
                                    <select name="rating" class="form-select">
                                        <option value="5" selected>★★★★★ - 5 Stars (Excellent)</option>
                                        <option value="4">★★★★☆ - 4 Stars (Very Good)</option>
                                        <option value="3">★★★☆☆ - 3 Stars (Average)</option>
                                        <option value="2">★★☆☆☆ - 2 Stars (Fair)</option>
                                        <option value="1">★☆☆☆☆ - 1 Star (Poor)</option>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold small">Your Review</label>
                                    <textarea name="reviewText" class="form-control" rows="4" maxlength="2000" placeholder="Share your experience with this product..." required></textarea>
                                </div>

                                <button class="btn btn-success w-100 py-2 fw-semibold" type="submit">
                                    Submit Review
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-light border p-3 text-center mb-0">
                                <i class="bi bi-lock-fill text-muted display-6 d-block mb-2"></i>
                                <h6 class="fw-bold">Sign In to Review</h6>
                                <p class="small text-muted mb-3">Please sign in to your ShopSphere account to share your verified product feedback.</p>
                                <a class="btn btn-outline-success btn-sm px-4 fw-semibold" href="${pageContext.request.contextPath}/login">Sign In</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>

    <!-- Recently Viewed Section -->
    <c:if test="${not empty recentlyViewed}">
        <section class="mt-5 pt-5 border-top">
            <h3 class="fw-bold mb-4 d-flex align-items-center gap-2">
                <i class="bi bi-clock-history text-secondary"></i> Recently Viewed
            </h3>
            <div class="row row-cols-2 row-cols-md-3 row-cols-lg-5 g-3">
                <c:forEach var="recent" items="${recentlyViewed}">
                    <div class="col">
                        <a class="recent-card h-100" href="${pageContext.request.contextPath}/product-details?id=${recent.productId}">
                            <div class="recent-img">
                                <c:choose>
                                    <c:when test="${not empty recent.imageUrl}">
                                        <c:set var="resolvedRecentImg" value="${recent.imageUrl.startsWith('http') ? recent.imageUrl : (recent.imageUrl.startsWith('/') ? pageContext.request.contextPath.concat(recent.imageUrl) : pageContext.request.contextPath.concat('/').concat(recent.imageUrl))}" />
                                        <img src="${resolvedRecentImg}" alt="<c:out value='${recent.name}'/>">
                                    </c:when>
                                    <c:otherwise>
                                        <i class="bi bi-box-seam text-muted fs-2"></i>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="p-3">
                                <small class="text-muted d-block text-truncate"><c:out value="${recent.brand}"/></small>
                                <h6 class="fw-bold text-dark text-truncate mb-1"><c:out value="${recent.name}"/></h6>
                                <div class="text-success fw-bold">₹<c:out value="${recent.price}"/></div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </section>
    </c:if>
</main>

<%@ include file="fragments/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
