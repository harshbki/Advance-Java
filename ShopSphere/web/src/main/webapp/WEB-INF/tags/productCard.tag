<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ attribute name="product" required="true" type="com.shopsphere.model.Product" %>
<article class="product-card" style="position: relative;">
    <c:if test="${product.discount gt 0}">
        <span class="discount-badge" style="position: absolute; top: 10px; left: 10px; background: #e11d48; color: #fff; font-size: 11px; font-weight: 700; padding: 3px 8px; border-radius: 4px; z-index: 2; box-shadow: 0 2px 6px rgba(225,29,72,0.3);">
            <fmt:formatNumber value="${product.discount}" maxFractionDigits="0"/>% OFF
        </span>
    </c:if>
    <a class="product-image" href="${pageContext.request.contextPath}/product-details?id=${product.productId}">
        <c:choose>
            <c:when test="${not empty product.imageUrl}">
                <c:set var="resolvedImg" value="${product.imageUrl.startsWith('http') ? product.imageUrl : (product.imageUrl.startsWith('/') ? pageContext.request.contextPath.concat(product.imageUrl) : pageContext.request.contextPath.concat('/').concat(product.imageUrl))}" />
                <img src="${resolvedImg}" alt="<c:out value='${product.name}'/>" loading="lazy"/>
            </c:when>
            <c:otherwise><span>ShopSphere</span></c:otherwise>
        </c:choose>
    </a>
    <div class="product-info">
        <p class="eyebrow"><c:out value="${product.brand}"/></p>
        <h2><c:out value="${product.name}"/></h2>
        <p class="price">₹<fmt:formatNumber value="${product.price}" pattern="#,##0.00"/><c:if test="${product.discount gt 0}"> <span class="discount" style="color: #15803d; font-size: 13px; font-weight: 600;">(<fmt:formatNumber value="${product.discount}" maxFractionDigits="0"/>% off)</span></c:if></p>
        <div style="display: flex; gap: 0.5rem; align-items: center; margin-top: 0.5rem;">
            <c:if test="${product.stock gt 0}">
                <form method="post" action="${pageContext.request.contextPath}/cart/add" style="margin: 0; flex: 1;">
                    <input type="hidden" name="productId" value="${product.productId}"/>
                    <input type="hidden" name="quantity" value="1"/>
                    <button class="button w-100" type="submit">Add to cart</button>
                </form>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/wishlist/add" style="margin: 0;">
                <input type="hidden" name="productId" value="${product.productId}"/>
                <button class="button secondary" type="submit" title="Add to Wishlist" style="padding: 0.55rem 0.85rem; font-size: 1rem; line-height: 1;">♡</button>
            </form>
        </div>
    </div>
</article>
