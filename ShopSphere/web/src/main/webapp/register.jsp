<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Create account | ShopSphere</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous"><link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop.css"></head>
<body><header class="topbar"><a class="brand" href="${pageContext.request.contextPath}/">ShopSphere</a><nav><a href="${pageContext.request.contextPath}/login">Sign in</a></nav></header>
<main class="auth-wrap"><section class="panel"><p class="eyebrow">JOIN SHOPSPHERE</p><h1>Create account</h1>
<c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>
<form method="post" action="${pageContext.request.contextPath}/register"><label>Name<input type="text" name="name" maxlength="120" autocomplete="name" required></label><label>Email<input type="email" name="email" maxlength="190" autocomplete="email" required></label><label>Mobile <span class="muted">(optional)</span><input type="tel" name="mobile" maxlength="25" autocomplete="tel"></label><label>Password<input type="password" name="password" minlength="8" autocomplete="new-password" required></label><button class="button" type="submit">Create account</button></form>
<p class="muted">Already registered? <a href="${pageContext.request.contextPath}/login">Sign in</a></p></section></main></body></html>
