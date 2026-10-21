<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav>
    <a href="${pageContext.request.contextPath}/products">Shop</a>
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/cart" class="nav-cart-link">🛒 Cart <c:if test="${not empty sessionScope.cartCount && sessionScope.cartCount > 0}"><span class="cart-badge" style="background:#335c45;color:#fff;border-radius:10px;padding:2px 7px;font-size:11px;font-weight:700;margin-left:2px;">${sessionScope.cartCount}</span></c:if></a>
            <a href="${pageContext.request.contextPath}/wishlist">Wishlist</a>
            <a href="${pageContext.request.contextPath}/orders">Orders</a>
            <a href="${pageContext.request.contextPath}/profile">Profile</a>
            <form method="post" action="${pageContext.request.contextPath}/logout" style="display:inline;margin:0;"><button class="link-button" type="submit">Sign out</button></form>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/login">Login</a>
            <a href="${pageContext.request.contextPath}/register">Register</a>
        </c:otherwise>
    </c:choose>
</nav>
