<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>Internationalization demonstration</title></head><body><h1><c:out value="${welcome}"/></h1><p><c:out value="${dateLabel}"/>: <c:out value="${formattedDate}"/></p><p>Price: <c:out value="${formattedPrice}"/></p><a href="?lang=en">English</a> · <a href="?lang=hi">Hindi</a></body></html>
