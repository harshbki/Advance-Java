<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="value" required="true" type="java.lang.Integer" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<span class="rating" aria-label="Rating ${value} out of 5"><c:forEach begin="1" end="5" var="star"><c:choose><c:when test="${star le value}">★</c:when><c:otherwise>☆</c:otherwise></c:choose></c:forEach></span>
