<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Wishlist | ShopSphere</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
    <style>
        .wishlist-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 1.5rem; margin-top: 1.5rem; }
        .wishlist-card { background: #fff; border: 1px solid #e2e8f0; border-radius: 12px; overflow: hidden; box-shadow: 0 1px 3px rgba(0,0,0,0.05); display: flex; flex-direction: column; }
        .wishlist-img-wrap { width: 100%; height: 200px; background: #f8fafc; overflow: hidden; position: relative; }
        .wishlist-img { width: 100%; height: 100%; object-fit: cover; transition: transform 0.3s ease; }
        .wishlist-img:hover { transform: scale(1.05); }
        .wishlist-body { padding: 1.25rem; flex: 1; display: flex; flex-direction: column; justify-content: space-between; }
        .wishlist-actions { display: flex; gap: 0.5rem; margin-top: 1rem; }
    </style>
</head>
<body>
<header class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/">ShopSphere</a>
    <nav>
        <a href="${pageContext.request.contextPath}/products">Shop</a>
        <a href="${pageContext.request.contextPath}/cart">Cart</a>
        <a href="${pageContext.request.contextPath}/orders">Orders</a>
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
    </nav>
</header>

<main class="content">
    <p class="eyebrow">SAVED ITEMS</p>
    <h1>Your wishlist</h1>

    <c:choose>
        <c:when test="${empty products}">
            <div class="alert alert-info py-4 text-center">
                <h4>Your wishlist is empty</h4>
                <p class="text-muted">You haven't added any products to your wishlist yet.</p>
                <a href="${pageContext.request.contextPath}/products" class="button">Browse products to save</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="wishlist-grid">
                <c:forEach var="p" items="${products}">
                    <div class="wishlist-card">
                        <a href="${pageContext.request.contextPath}/product-details?id=${p.productId}" class="wishlist-img-wrap">
                            <img src="${p.imageUrl.startsWith('http') ? p.imageUrl : pageContext.request.contextPath.concat('/').concat(p.imageUrl)}" alt="${p.name}" class="wishlist-img" style="object-fit: contain; background: #fff;" onerror="this.src='https://placehold.co/300x200?text=Product'">
                        </a>
                        <div class="wishlist-body">
                            <div>
                                <p class="eyebrow mb-1"><c:out value="${p.brand}"/></p>
                                <h3 style="font-size: 1.05rem; font-weight: 600; margin-bottom: 0.5rem;">
                                    <a href="${pageContext.request.contextPath}/product-details?id=${p.productId}" style="text-decoration: none; color: inherit;">
                                        <c:out value="${p.name}"/>
                                    </a>
                                </h3>
                                <p class="price" style="font-size: 1.15rem; font-weight: 700; color: #0f172a; margin-bottom: 0.25rem;">
                                    ₹<c:out value="${p.price}"/>
                                </p>
                                <span class="badge ${p.stock > 0 ? 'bg-success' : 'bg-danger'}">
                                    <c:out value="${p.stock > 0 ? 'In Stock' : 'Out of Stock'}"/>
                                </span>
                            </div>

                            <div class="wishlist-actions">
                                <c:if test="${p.stock > 0}">
                                    <form method="post" action="${pageContext.request.contextPath}/cart/add" style="margin: 0; flex: 1;">
                                        <input type="hidden" name="productId" value="${p.productId}">
                                        <input type="hidden" name="quantity" value="1">
                                        <button class="button w-100" type="submit" style="padding: 0.5rem;">Move to cart</button>
                                    </form>
                                </c:if>
                                <form method="post" action="${pageContext.request.contextPath}/wishlist/remove" style="margin: 0;">
                                    <input type="hidden" name="productId" value="${p.productId}">
                                    <button class="button secondary" type="submit" title="Remove from Wishlist" style="padding: 0.5rem 0.75rem; color: #dc2626;">✕</button>
                                </form>
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
