<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><title>JSTL XML demo</title></head><body>
<h1>JSTL XML demonstration</h1>
<p>This isolated syllabus page demonstrates parsing and traversing XML feeds using JSTL XML tags (<code>&lt;x:parse&gt;</code>, <code>&lt;x:forEach&gt;</code>, <code>&lt;x:out&gt;</code>).</p>
<c:set var="catalog">
<catalog>
    <product><name>Sample notebook</name><price>120.00</price></product>
    <product><name>Sample desk lamp</name><price>850.00</price></product>
    <product><name>Java Programming Guide</name><price>750.00</price></product>
</catalog>
</c:set>
<x:parse xml="${catalog}" var="doc"/>
<div style="background:#fff;border:1px solid #e5e6df;border-radius:6px;padding:16px;max-width:500px;margin-top:16px;">
    <h3>Parsed Product Feed:</h3>
    <x:forEach select="$doc/catalog/product" var="item">
        <p><strong><x:out select="$item/name"/></strong> — ₹<x:out select="$item/price"/></p>
    </x:forEach>
</div>
</body></html>
