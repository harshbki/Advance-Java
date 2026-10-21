<%@ page contentType="text/html;charset=UTF-8" %>
<%! private int demoCount = 1; %>
<% String demoName = "Student"; %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>JSP syntax syllabus demo</title></head><body>
<h1>Traditional JSP elements demonstration</h1>
<p>Declaration value: <%= demoCount %></p>
<p>Scriptlet value: <%= demoName %></p>
<%-- This is a JSP comment; it is not sent as page content. --%>
<p>The main ShopSphere pages use EL and JSTL for rendering.</p>
</body></html>
