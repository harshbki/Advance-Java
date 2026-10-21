<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>JSTL core and functions demo</title></head><body>
<h1>JSTL Core and Functions demonstration</h1>
<c:set var="topics" value="Servlet,JSP,JDBC,JSTL"/>
<p>Topic count: ${fn:length(fn:split(topics, ','))}</p>
<c:forEach var="topic" items="${fn:split(topics, ',')}"><p><c:out value="${topic}"/></p></c:forEach>
<c:choose><c:when test="${not empty topics}">The example list is ready.</c:when><c:otherwise>No examples.</c:otherwise></c:choose>
</body></html>
