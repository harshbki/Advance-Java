<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${applicationScope.appName}"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css">
</head>
<body>
<header class="topbar"><a class="brand" href="${pageContext.request.contextPath}/">ShopSphere</a><nav><a href="${pageContext.request.contextPath}/products">Shop</a><a href="${pageContext.request.contextPath}/login">Login</a></nav></header>
<main class="hero"><p class="eyebrow">EVERYDAY FINDS, MADE SIMPLE</p><h1>Find something<br>you’ll love.</h1><p>Browse the ShopSphere collection and discover your next favourite.</p><a class="button" href="${pageContext.request.contextPath}/products">Explore products</a></main>
</body>
</html>
