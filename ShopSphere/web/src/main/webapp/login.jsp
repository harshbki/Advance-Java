<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Sign in | ShopSphere</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous"><link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css"></head>
<body><header class="topbar"><a class="brand" href="${pageContext.request.contextPath}/">ShopSphere</a><nav><a href="${pageContext.request.contextPath}/products">Shop</a></nav></header>
<main class="auth-wrap"><section class="panel"><p class="eyebrow">WELCOME BACK</p><h1>Sign in</h1>
<c:if test="${not empty param.registered}"><p class="notice">Account created. You can sign in now.</p></c:if>
<c:if test="${not empty param.logout}"><p class="notice">You have signed out.</p></c:if>
<c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>
<c:if test="${adminMode}"><p class="notice">Administrator Portal Login</p></c:if>
<form method="post" action="${pageContext.request.contextPath}${adminMode ? '/admin/login' : '/login'}"><label>Email<input type="email" name="email" autocomplete="email" required></label><label>Password<input type="password" name="password" autocomplete="current-password" required></label><button class="button" type="submit">${adminMode ? 'Sign in to Admin Dashboard' : 'Sign in'}</button></form>
<p class="muted">New to ShopSphere? <a href="${pageContext.request.contextPath}/register">Create an account</a></p></section></main></body></html>
