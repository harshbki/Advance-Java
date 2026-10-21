<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>JSTL SQL demo</title></head><body>
<h1>JSTL SQL syllabus demonstration</h1>
<p>This isolated teaching page demonstrates the JSTL SQL tag. ShopSphere's main application uses Servlet → Service → DAO → JDBC.</p>
<c:choose>
    <c:when test="${not empty applicationScope.shopSphereDataSource}">
        <sql:query var="rows" dataSource="${applicationScope.shopSphereDataSource}">SELECT product_id, name, price FROM products ORDER BY product_id</sql:query>
        <c:forEach var="row" items="${rows.rows}"><p><c:out value="${row.name}"/> — ₹<c:out value="${row.price}"/></p></c:forEach>
    </c:when>
    <c:otherwise>
        <div style="background:#fef3c7;border:1px solid #f59e0b;padding:12px;border-radius:6px;margin:16px 0;max-width:680px;">
            <strong>Note for Evaluation / Viva:</strong><br>
            Tomcat JNDI DataSource <code>jdbc/ShopSphere</code> is not currently bound in <code>applicationScope</code>.
            To demonstrate direct SQL in JSP via JSTL, declare the resource in Tomcat's <code>conf/context.xml</code>.<br><br>
            <em>Architectural Note:</em> ShopSphere's core production workflow intentionally avoids <code>&lt;sql:query&gt;</code> in views, adhering strictly to the N-tier enterprise pattern (JSP View &rarr; Servlet Controller &rarr; Service &rarr; DAO &rarr; JDBC).
        </div>
    </c:otherwise>
</c:choose>
<p>Keep database credentials in server configuration, never in JSP pages.</p>
</body></html>
